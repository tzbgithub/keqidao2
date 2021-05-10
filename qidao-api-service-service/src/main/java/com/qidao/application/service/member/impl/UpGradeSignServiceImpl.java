
package com.qidao.application.service.member.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.entity.log.LogMemberLogin;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.entity.relation.LinkSelectExample;
import com.qidao.application.mapper.config.SelectConfigMapper;
import com.qidao.application.mapper.invite.CustomInviteMapper;
import com.qidao.application.mapper.log.LogMemberLoginMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.mapper.relation.LinkSelectMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.VerifyCodeUtils;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dict.DictConstantEnum;
import com.qidao.application.model.dynamic.comment.RegisterEventParam;
import com.qidao.application.model.easemob.EasemobBaseDTO;
import com.qidao.application.model.easemob.EasemobRegisterNoTokenReq;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.MemberIndustryRes;
import com.qidao.application.model.member.sign.*;
import com.qidao.application.model.member.token.GeneratorAccessTokenReq;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.service.chuanglan.*;
import com.qidao.application.service.event.PublishEventComponent;
import com.qidao.application.service.event.member.MemberAgreeEvent;
import com.qidao.application.service.im.EasemobUserService;
import com.qidao.application.service.member.MemberTokenService;
import com.qidao.application.service.member.SignService;
import com.qidao.application.service.member.other.AsyncLoginComponent;
import com.qidao.application.service.member.other.ILoginStrategy;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Primary
@Service
@Slf4j
public class UpGradeSignServiceImpl implements SignService {

    @Value("${chuanglan.flash.login.url}")
    private String url;
    @Value("${chuanglan.flash.login.appId}")
    private String appId;
    @Value("${chuanglan.flash.login.appKey}")
    private String appKey;
    @Value("${chuanglan.flash.login.ios.appId}")
    private String appIdIos;
    @Value("${chuanglan.flash.login.ios.appKey}")
    private String appKeyIos;
    @Autowired
    private MemberTokenService memberTokenService;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;
    @Resource
    private LogMemberLoginMapper logMemberLoginMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Resource
    private OrganizationMapper organizationMapper;
    @Autowired
    private EasemobUserService easemobUserService;
    @Autowired
    private PublishEventComponent publishEventComponent;
    @Autowired
    private LinkSelectMapper linkSelectMapper;
    @Resource
    private MemberRedissonService memberRedissonService;
    @Autowired
    private AsyncLoginComponent asyncLoginComponent;
    @Autowired
    private SelectConfigMapper selectConfigMapper;
    @Autowired
    private CustomMemberMapper customMemberMapper;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private CustomInviteMapper customInviteMapper;

    /**
     * 登录实现 列表
     */
    @Autowired
    private List<ILoginStrategy> loginStrategyList;
    private Map<Integer, ILoginStrategy> loginStrategyMap;

    @PostConstruct
    public void init() {
        loginStrategyMap = new HashMap<>(loginStrategyList.size());
        for (ILoginStrategy iLoginStrategy : loginStrategyList) {
            loginStrategyMap.put(iLoginStrategy.getLoginType().getType(), iLoginStrategy);
        }
    }

    @Override
    public SignInRes signIn(SignInReq req) {
        log.info("UpGradeSignServiceImpl -> signIn -> begin -> {}", req);
        // 账户密码登录
        LocalDateTime now = LocalDateTime.now();
        // 身份验证
        ILoginStrategy iLoginStrategy = loginStrategyMap.get(req.getType());
        if (iLoginStrategy == null) {
            throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(def);

        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        try{
            Member member = iLoginStrategy.login(req);
            if (StringUtils.isBlank(member.getImEasemobUsername())) {
                setImEasemobActive(member);
                MemberExample memberExample = new MemberExample();
                memberExample.createCriteria().andIdEqualTo(member.getId());
                member.setImEasemobUsername(String.valueOf(member.getId()));
                memberMapper.updateByExampleSelective(member, memberExample);
            }

            BeanUtils.copyProperties(member, memberInfoDTO);
            memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(memberInfoDTO.getId())));

            //修改 邀请状态 1-已登录APP绑定邀请人
            if (StrUtil.isNotBlank(member.getPhone())) {
                customInviteMapper.updateInviteStatusByInvitePhone(member.getPhone());
            }
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transaction);
            throw e;
        }

        // 生成 refresh token
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .canal(req.getCanal())
                .loginTime(now)
                .version(req.getVersion())
                .build();

        SignInRes result = new SignInRes();
        result.setMemberInfo(memberInfoDTO);

        //开启异步任务
        try {
            CompletableFuture<Void> tokenFuture = CompletableFuture.supplyAsync(() -> {
                String refreshToken = createRefreshToken(generatorRefreshTokenDTO, memberInfoDTO);
                result.setRefreshToken(refreshToken);
                return refreshToken;
            },threadPoolExecutor).thenAccept(refreshToken -> {
                // 生成 access token
                String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));
                result.setAccessToken(accessToken);
            });
            CompletableFuture.allOf(
                    CompletableFuture.runAsync(() -> {
                        // 环信IM强制退出
                        easemobUserService.disconnect(String.valueOf(memberInfoDTO.getId()));
                    },threadPoolExecutor),
                    CompletableFuture.runAsync(() -> {
                        //设置Flag
                        setFlag(memberInfoDTO);
                    }, threadPoolExecutor),
                    CompletableFuture.runAsync(() -> {
                        //设置OrganizationType
                        setOrganizationType(memberInfoDTO.getOrganizationId(), memberInfoDTO);
                    }, threadPoolExecutor),
                    CompletableFuture.runAsync(() -> {
                        //设置行业
                        setMemberIndustry(memberInfoDTO);
                    }, threadPoolExecutor),
                    tokenFuture
            ).get(5L, TimeUnit.SECONDS);
        }  catch (Exception e) {
            log.error("UpGradeSignServiceImpl -> signIn -> 异步处理失败 -> exception:{}", e);
            throw new BusinessException("登录失败");
        }

        // 提交登录事件
        LogMemberLogin logMemberLogin = LogMemberLogin.builder()
                .canal(req.getCanal())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .id(snowflakeIdWorker53.nextId())
                .ip(req.getIp())
                .version(req.getVersion())
                .build();
        //登录异步处理  todo 有大量sql update 操作，并发占用db资源，限制线程数量
        asyncLoginComponent.asyncSignIn(generatorRefreshTokenDTO, logMemberLogin);
        return result;
    }

    /**
     * 一键登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AutoSignRes shortCutSignIn(AutoSignReq req) {
        log.info("UpGradeSignServiceImpl -> shortcutSignIn -> begin -> {}", req);
        // 身份验证
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(req.getPhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            log.info("UpGradeSignServiceImpl -> shortcutSignIn -> 用户不存在自动生成并返回信息 -> ");
            member = createMember(req.getPhone());
            memberMapper.insertSelective(member);
        } else {
            if (StringUtils.isBlank(member.getImEasemobUsername())) {
                setImEasemobActive(member);
                member.setImEasemobUsername(String.valueOf(member.getId()));
                memberMapper.updateByExampleSelective(member, memberExample);
            }
            log.info("UpGradeSignServiceImpl -> shortcutSignIn -> 用户存在返回信息");
        }
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        BeanUtils.copyProperties(member, memberInfoDTO);
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .canal(req.getCanal())
                .loginTime(LocalDateTime.now())
                .version(req.getVersion())
                .build();
        // 生成 refresh token
        String refreshToken = createRefreshToken(generatorRefreshTokenDTO, memberInfoDTO);
        // 生成 access token
        String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));
        log.info("UpGradeSignServiceImpl -> shortcutSignIn -> end");
        return AutoSignRes.builder()
                .memberInfo(memberInfoDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * 创蓝第三方登录验证
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AutoSignRes shortChuanglanSignIn(AutoChuangLanReq req, HttpServletRequest request) {
        String app_id = null;
        String app_key = null;
        String agent = request.getHeader("User-Agent");
        if (agent.contains("Android")) {//安卓手机
            app_id = appId;
            app_key = appKey;
        } else if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad")) {//苹果手机
            app_id = appIdIos;
            app_key = appKeyIos;
        }
        log.info("UpGradeSignServiceImpl -> shortChuanglanSignIn -> start -> req -> {}", req);
        //手机号加解密方式 0 AES 1 RSA , 可以不传，不传则手机号解密直接使用AES解密

        String token = req.getToken();
        String encryptType = req.getEncryptType();
        if (StringUtils.isBlank(encryptType)) {
            encryptType = "0";
        }
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
        }
        String mobile = null;
        try {
            Map<String, String> params = new HashMap<>(5);
            params.put("token", req.getToken());
            params.put("clientIp", req.getIp());
            params.put("appId", app_id);
            //可以不传，不传则解密直接使用AES解密
            params.put("encryptType", encryptType);
            params.put("sign", SignUtils.getSign(params, app_key));
            log.info("UpGradeSignServiceImpl -> shortChuanglanSignIn -> 请求创蓝参数{}", params);
            JSONObject jsonObject = OkHttpUtil.postRequest(url, params);
            if (null != jsonObject) {
                log.info("-----UpGradeSignServiceImpl---- 创蓝返回信息:{}", jsonObject);
                //返回码 200000为成功
                String code = jsonObject.getString("code");
                if ("200000".equals(code)) {
                    String dataStr = jsonObject.getString("data");
                    JSONObject dataObj = JSONObject.parseObject(dataStr);
                    mobile = dataObj.getString("mobileName");
                    if ("0".equals(encryptType)) {
                        String key = MD5.getMD5Code(app_key);
                        mobile = AESUtils.decrypt(mobile, key.substring(0, 16), key.substring(16));
                    } else if ("1".equals(encryptType)) {
                        mobile = RSAUtils.decryptByPrivateKeyForLongStr(mobile, "");
                    }
                    log.info("-----UpGradeSignServiceImpl---- 解密后的手机号码:{}", mobile);
                } else {
                    log.error("----UpGradeSignServiceImpl 调用创蓝闪验失败----返回码:{} -----", code);
                    throw new BusinessException("登录失败");
                }
            } else {
                log.error("----UpGradeSignServiceImpl 调用创蓝闪验失败---返回信息为空---------");
                throw new BusinessException("登录失败");
            }
        } catch (Exception e) {
            log.error("----UpGradeSignServiceImpl 调用创蓝查询手机号失败 -----", e);
            throw new BusinessException("登录失败");
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(mobile);
        Member member = memberMapper.selectOneByExample(memberExample);
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        if (member == null) {
            log.info("UpGradeSignServiceImpl -> shortChuanglanSignIn -> 用户不存在自动生成并返回信息 -> ");
            member = createMember(mobile);
            memberMapper.insertSelective(member);
            BeanUtils.copyProperties(member, memberInfoDTO);
            memberInfoDTO.setFlag(false);
        } else {
            if (StringUtils.isBlank(member.getImEasemobUsername())) {
                setImEasemobActive(member);
                member.setImEasemobUsername(String.valueOf(member.getId()));
                memberMapper.updateByExampleSelective(member, memberExample);
            }
            log.info("UpGradeSignServiceImpl -> shortChuanglanSignIn -> 用户存在返回信息");
            BeanUtils.copyProperties(member, memberInfoDTO);
            //设置Flag
            setFlag(memberInfoDTO);
            //设置OrganizationType
            setOrganizationType(member, memberInfoDTO);
        }

        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .canal(req.getCanal())
                .loginTime(LocalDateTime.now())
                .version(req.getVersion())
                .build();
        // 生成 refresh token
        String refreshToken = createRefreshToken(generatorRefreshTokenDTO, memberInfoDTO);
        // 生成 access token
        String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));

        AutoSignRes autoSignRes = new AutoSignRes();
        autoSignRes.setAccessToken(accessToken);
        autoSignRes.setRefreshToken(refreshToken);
        autoSignRes.setMemberInfo(memberInfoDTO);

        log.info("UpGradeSignServiceImpl -> shortChuanglanSignIn -> end -> res -> {}", autoSignRes);
        return autoSignRes;
    }

    /**
     * 设置 会员 行业信息 列表
     *
     * @param memberInfoDTO
     */
    private void setMemberIndustry(MemberInfoDTO memberInfoDTO) {
        List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(memberInfoDTO.getId());
        List<MemberIndustryRes> memberIndustryResList = memberIndustry.stream()
                .map(item -> {
                    MemberIndustryRes memberIndustryRes = new MemberIndustryRes();
                    memberIndustryRes.setId(item.getId());
                    memberIndustryRes.setVal(item.getVal());
                    return memberIndustryRes;
                }).collect(Collectors.toList());
        memberInfoDTO.setMemberIndustry(memberIndustryResList);
    }


    /**
     * 会员注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignUpRes signUp(SignUpReq req) {
        String code = req.getCode();
        RBucket<String> bucket = redissonClient.getBucket("sms::send::code::" + req.getPhone());
        String redisCode = bucket.get();
        if (redisCode == null || "".equals(redisCode)) {
            log.info("UpGradeSignServiceImpl -> signUp -> 验证码不存在导致过期 ->");
            throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
        }
        if (!code.equals(redisCode)) {
            log.info("UpGradeSignServiceImpl -> signUp -> 验证码不匹配 redis code{} -> 请求code{}  请求人{}", code, redisCode, req.getPhone());
            throw new BusinessException(BaseEnum.VERIFY_CODE_ERROR);
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(req.getPhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        if (member == null) {
            log.info("UpGradeSignServiceImpl -> signUp -> 用户不存在自动生成并返回信息 -> ");
            member = createMember(req.getPhone(), req.getPassword())
                    .setNo(req.getName());
            memberMapper.insertSelective(member);
        } else {
            throw new BusinessException("用户已注册");
        }

        SignUpRes signUpRes = new SignUpRes();
        signUpRes.setMemberId(member.getId());
        signUpRes.setPhone(req.getPhone());
        signUpRes.setName(req.getName());

        publishEventComponent.publishEvent(new MemberAgreeEvent(RegisterEventParam.builder()
                .memberId(member.getId())
                .build()));

        return signUpRes;
    }

    /**
     * 统一调用环信注册接口
     *
     * @param id
     * @return
     */
    private Boolean invokingHxRegister(Long id) {
        Boolean activated = null;
        try {
            EasemobRegisterNoTokenReq easemobRegisterNoTokenReq = new EasemobRegisterNoTokenReq();
            ArrayList<EasemobRegisterNoTokenReq.User> users = new ArrayList<EasemobRegisterNoTokenReq.User>();
            EasemobRegisterNoTokenReq.User user = new EasemobRegisterNoTokenReq.User();
            user.setUsername(id);
            user.setPassword(Md5Util.getCrypt(String.valueOf(id)));
            users.add(user);
            easemobRegisterNoTokenReq.setUsers(users);
            EasemobBaseDTO easemobBaseDTO = easemobUserService.registerNoToken(easemobRegisterNoTokenReq);
            log.info("UpGradeSignServiceImpl -> invokingHxRegister -> 返回结果 -> {}", easemobBaseDTO);
            activated = easemobBaseDTO.getActivated();
        } catch (Exception e) {
            log.info("UpGradeSignServiceImpl -> invokingHxRegister -> 错误信息 -> {}", e);
            throw new BusinessException(BaseEnum.NET_UNSTABLE_ERROR);
        }
        return activated;
    }


    private void setImEasemobActive(Member member) {
        member.setImEasemobActive(invokingHxRegister(member.getId()) ?
                MemberEnum.VERIFY_STATUS_FAIL.getValue() : MemberEnum.VERIFY_STATUS_CLOSE.getValue());
    }

    private void setFlag(MemberInfoDTO memberInfoDTO) {
        LinkSelectExample linkSelectExample = new LinkSelectExample();
        linkSelectExample.createCriteria()
                .andSourceIdEqualTo(memberInfoDTO.getId())
                .andTypeEqualTo(DictConstantEnum.INDUSTRY.getId())
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        long count = linkSelectMapper.countByExample(linkSelectExample);
        memberInfoDTO.setFlag(count > 0);
    }

    private void setOrganizationType(Member member, MemberInfoDTO memberInfoDTO) {
        if (member.getOrganizationId() != null) {
            memberInfoDTO.setOrganizationType(
                    Optional.ofNullable(organizationMapper.selectByPrimaryKey(member.getOrganizationId()))
                            .map(Organization::getType)
                            .orElse(null)
            );
        }
    }

    private void setOrganizationType(Long organizationId, MemberInfoDTO memberInfoDTO) {
        if (organizationId != null) {
            memberInfoDTO.setOrganizationType(
                    Optional.ofNullable(organizationMapper.selectByPrimaryKey(organizationId))
                            .map(Organization::getType)
                            .orElse(null)
            );
        }
    }

    /**
     * 生成 refresh token
     *
     * @param generatorRefreshTokenDTO
     * @param memberInfoDTO
     * @return java.lang.String
     * @Author zhousen
     * @Date 2021/04/01 10:37
     **/
    private String createRefreshToken(GeneratorRefreshTokenDTO generatorRefreshTokenDTO, MemberInfoDTO memberInfoDTO) {

        //设置组织机构vip结束时间
        /*OrganizationExample example = new OrganizationExample();
        example.createCriteria()
                .andResponsibleMemberIdEqualTo(memberInfoDTO.getId());
        Organization organization = organizationMapper.selectOneByExample(example);
        if(organization != null){
            memberInfoDTO.setOrganizationVipEndTime(organization.getVipEndTime());
        }*/

        //生成的用户信息存入缓存
        String memberId = String.valueOf(memberInfoDTO.getId());
        memberInfoDTO.setPassword(Md5Util.getCrypt(memberId));
        //标识登录 generatorAccessToken 中判断需要使用
        RBucket<String> bucket = memberRedissonService.getMemberFirstLoginId(memberInfoDTO.getId());
        bucket.set(memberId, 1L, TimeUnit.MINUTES);
        return memberTokenService.generatorRefreshTokenNotToDB(generatorRefreshTokenDTO);
    }

    /**
     * 创建会员信息
     *
     * @param phone
     * @return com.qidao.application.entity.member.Member
     * @Author zhousen
     * @Date 2021/03/31 17:11
     **/
    private Member createMember(String phone) {
        return createMember(phone, null);
    }

    /**
     * 创建会员信息
     *
     * @param phone
     * @return com.qidao.application.entity.member.Member
     * @Author zhousen
     * @Date 2021/03/31 17:11
     **/
    private Member createMember(String phone, String password) {
        if (password == null) {
            password = "123456";
        }
        long id = snowflakeIdWorker53.nextId();
        Member member = new Member();
        member.setCreateTime(LocalDateTime.now())
                .setPhone(phone)
                .setRole(MemberEnum.ROLE_ORDINARY.getValue())
                .setId(id)
                .setLevel(MemberEnum.LEVEL_ORDINARY.getValue())
                .setPassword(Md5Util.getCrypt(password))
                .setName(VerifyCodeUtils.generateInviteCodeAndNotNum(10))
                .setPushStatus(MemberEnum.PUSH_STATUS_CLOSE.getValue())
                .setDelFlag(ConstantEnum.NOT_DEL.getByte())
                .setImEasemobUsername(String.valueOf(id));
        setImEasemobActive(member);
        return member;
    }

}
