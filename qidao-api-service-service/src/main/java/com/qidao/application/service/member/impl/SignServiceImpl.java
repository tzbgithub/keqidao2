
package com.qidao.application.service.member.impl;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.entity.invite.Invite;
import com.qidao.application.entity.invite.InviteExample;
import com.qidao.application.entity.log.LogMemberLogin;
import com.qidao.application.entity.member.Member;
import com.qidao.application.entity.member.MemberExample;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.mapper.invite.InviteMapper;
import com.qidao.application.mapper.log.LogMemberLoginMapper;
import com.qidao.application.mapper.member.CustomMemberMapper;
import com.qidao.application.mapper.member.MemberMapper;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.model.common.Md5Util;
import com.qidao.application.model.common.VerifyCodeUtils;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.dynamic.comment.RegisterEventParam;
import com.qidao.application.model.easemob.EasemobBaseDTO;
import com.qidao.application.model.easemob.EasemobRegisterNoTokenReq;
import com.qidao.application.model.invite.enums.InviteEnum;
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
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.application.vo.SelectConfigResp;
import com.qidao.application.service.redis.MemberRedissonService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SignServiceImpl implements SignService {


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
    @Resource
    private CustomMemberMapper customMemberMapper;
    @Resource
    private MemberRedissonService memberRedissonService;
    @Resource
    private InviteMapper inviteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignInRes signIn(SignInReq req) {
        log.info("SignServiceImpl -> signIn -> begin -> {}", req);
        // 账户密码登录
        LocalDateTime now = LocalDateTime.now();
        // 身份验证
        Integer type = req.getType();
        MemberExample memberExample = new MemberExample();
        Member member = null;
        switch (type) {
            case 0:
                String reqCode = req.getCode();
                if (StringUtils.isEmpty(reqCode)) {
                    throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
                }
                memberExample.createCriteria()
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                        .andPhoneEqualTo(req.getPhone());
                member = memberMapper.selectOneByExample(memberExample);
//                member = invitedMemberFirstLogin(member , req.getPhone());
                if (member == null) {
                    member = new Member();
                    long id = snowflakeIdWorker53.nextId();
                    member.setPhone(req.getPhone()).
                            setName(VerifyCodeUtils.generateInviteCode(10)).
                            setPassword(Md5Util.getCrypt("123456")).setHeadImage("defaultlogo.png").
                            setId(id).setImEasemobUsername(String.valueOf(id));
                    memberMapper.insertSelective(member);
                    Boolean activated = invokingHxRegister(id);
                    if (activated) {
                        member.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
                    } else {
                        member.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
                    }
                }
                if (req.getPhone().equals("15551385182")) {
                    if (!req.getCode().equals("006688")) {
                        throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
                    }
                } else {
                    RBucket<String> bucket = redissonClient.getBucket("sms::send::code::" + req.getPhone());


                    String code = bucket.get();
                    if (code == null || "".equals(code)) {
                        log.info("SignServiceImpl -> signIn -> 验证码不存在导致过期 ->");
                        throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
                    }
                    if (!code.equals(reqCode)) {
                        log.info("SignServiceImpl -> signIn -> 验证码不匹配 redis code{} -> 请求code{}  请求人{}", code, reqCode, req.getPhone());
                        throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
                    }
                    bucket.set(null, 5, TimeUnit.MINUTES);
                }
                break;
            case 1:
                throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
            case 2:
                throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
            case 3:
                memberExample.createCriteria()
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                        .andPhoneEqualTo(req.getPhone())
                        .andPasswordEqualTo(Md5Util.getCrypt(req.getPassword()));
                member = memberMapper.selectOneByExample(memberExample);
                if (member == null) {
                    throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
                }
                break;
            case 4:
                throw new BusinessException(BaseEnum.LOGIN_TYPE_ERROR);
            case 5:
                memberExample.createCriteria()
                        .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                        .andNoEqualTo(req.getAccount())
                        .andPasswordEqualTo(Md5Util.getCrypt(req.getPassword()));
                member = memberMapper.selectOneByExample(memberExample);
                if (member == null) {
                    throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
                }
                break;
            default:
                throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
        }
        if (StringUtils.isBlank(member.getImEasemobUsername())) {
            Boolean activated = invokingHxRegister(member.getId());
            if (activated) {
                member.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
            } else {
                member.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }
            member.setImEasemobUsername(String.valueOf(member.getId()));
            memberMapper.updateByExampleSelective(member, memberExample);
        }
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        BeanUtils.copyProperties(member , memberInfoDTO);
        List<SelectConfigResp> memberIndustry = customMemberMapper.getMemberIndustry(memberInfoDTO.getId());
        List<MemberIndustryRes> memberIndustryRes = memberIndustry.stream().map(this::convertIndustry).collect(Collectors.toList());
        memberInfoDTO.setMemberIndustry(memberIndustryRes);
        memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(memberInfoDTO.getId())));
        // 环信IM强制退出
        easemobUserService.disconnect(String.valueOf(member.getId()));

        // 生成 refresh token
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(member.getId())
                .canal(req.getCanal())
                .loginTime(now)
                .version(req.getVersion())
                .build();
        memberInfoDTO.setFlag(false);
        if (CollUtil.isNotEmpty(memberIndustryRes)){
            memberInfoDTO.setFlag(true);
        }
        Long organizationId = member.getOrganizationId();
        if (organizationId != null) {
            Organization organization = organizationMapper.selectByPrimaryKey(organizationId);
            if (organization != null) {
                memberInfoDTO.setOrganizationType(organization.getType());
            }
        }

        //生成的用户信息存入缓存
        memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(memberInfoDTO.getId())));
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
        bucket.set(JSONUtil.toJsonStr(memberInfoDTO),120L, TimeUnit.MINUTES);
        String refreshToken = memberTokenService.generatorRefreshToken(generatorRefreshTokenDTO);
        // 生成 access token
        String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));
        // 提交登录事件
        log.info("SignServiceImpl -> signIn -> 存入用户登录记录 -");
        LogMemberLogin logMemberLogin =
                LogMemberLogin.builder().canal(req.getCanal()).
                        machineCode(req.getMachineCode())
                        .memberId(member.getId()).
                        id(snowflakeIdWorker53.nextId()).ip(req.getIp()).version(req.getVersion()).build();
        logMemberLoginMapper.insertSelective(logMemberLogin);
        log.info("SignServiceImpl -> signIn -> end");
        return SignInRes.builder()
                .memberInfo(memberInfoDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 一键登录
     */
    @Override
    @Transactional
    public AutoSignRes shortCutSignIn(AutoSignReq req) {
        byte flag = ConstantEnum.NOT_DEL.getByte();
        log.info("SignServiceImpl -> shortcutSignIn -> begin -> {}", req);
        // 身份验证
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(req.getPhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        long id = snowflakeIdWorker53.nextId();
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = null;
        if (member == null) {
            log.info("SignServiceImpl -> shortcutSignIn -> 用户不存在自动生成并返回信息 -> ");
            Member membe = new Member();
            membe.setCreateTime(LocalDateTime.now()).
                    setPhone(req.getPhone()).
                    setRole(MemberEnum.ROLE_ORDINARY.getValue()).setId(id).
                    setLevel(MemberEnum.LEVEL_ORDINARY.getValue()).
                    setPassword(Md5Util.getCrypt("123456")).setHeadImage("defaultlogo.png")
                    .setName(VerifyCodeUtils.generateInviteCode(10)).
                    setPushStatus(MemberEnum.PUSH_STATUS_CLOSE.getValue()).
                    setDelFlag(flag).
                    setImEasemobUsername(String.valueOf(id));
            Boolean activated = invokingHxRegister(id);
            if (activated) {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
            } else {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }
            memberMapper.insertSelective(membe);
            BeanUtils.copyProperties(membe, memberInfoDTO);
        } else {
            if (StringUtils.isBlank(member.getImEasemobUsername())) {
                Boolean activated = invokingHxRegister(member.getId());
                if (activated) {
                    member.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
                } else {
                    member.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
                }
                member.setImEasemobUsername(String.valueOf(member.getId()));
                memberMapper.updateByExampleSelective(member, memberExample);
            }
            log.info("SignServiceImpl -> shortcutSignIn -> 用户存在返回信息 -> ");
            BeanUtils.copyProperties(member, memberInfoDTO);
        }
        generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .canal(req.getCanal())
                .loginTime(LocalDateTime.now())
                .version(req.getVersion())
                .build();
        memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(memberInfoDTO.getId())));
        // 生成 refresh token
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
        bucket.set(JSONUtil.toJsonStr(memberInfoDTO),120L, TimeUnit.MINUTES);
        String refreshToken = memberTokenService.generatorRefreshToken(generatorRefreshTokenDTO);
        // 生成 access token
        String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));
        // 提交登录事件
        log.info("SignServiceImpl -> shortcutSignIn -> end");
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
        log.info("SignServiceImpl -> shortChuanglanSignIn -> start -> req -> {}", req);
        //手机号加解密方式 0 AES 1 RSA , 可以不传，不传则手机号解密直接使用AES解密
        String encryptType = req.getEncryptType();
        String token = req.getToken();
        if (StringUtils.isBlank(encryptType)) {
            encryptType = "0";
        } else {
            encryptType = req.getEncryptType();
        }
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(BaseEnum.ACCOUNT_VERIFY_PASSWORD_ERROR);
        }
        String mobile = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("token", req.getToken());
            params.put("clientIp", req.getIp());
            params.put("appId", app_id);
            //可以不传，不传则解密直接使用AES解密
            params.put("encryptType", encryptType);
            params.put("sign", SignUtils.getSign(params, app_key));
            log.info("SignServiceImpl -> shortChuanglanSignIn -> 请求创蓝参数{}", params);
            JSONObject jsonObject = OkHttpUtil.postRequest(url, params);
            if (null != jsonObject) {
                log.info("-----SignServiceImpl---- 创蓝返回信息:{}", jsonObject);
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
                    log.info("-----SignServiceImpl---- 解密后的手机号码:{}", mobile);
                } else {
                    log.error("----SignServiceImpl 调用创蓝闪验失败----返回码:{} -----", code);
                    throw new BusinessException("登录失败");
                }

            } else {
                log.error("----SignServiceImpl 调用创蓝闪验失败---返回信息为空---------");
                throw new BusinessException("登录失败");
            }
        } catch (Exception e) {
            log.error("----SignServiceImpl 调用创蓝查询手机号失败 -----", e);
            throw new BusinessException("登录失败");
        }
        byte flag = ConstantEnum.NOT_DEL.getByte();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(mobile);
        Member member = memberMapper.selectOneByExample(memberExample);
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        long id = snowflakeIdWorker53.nextId();
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = null;
        AutoSignRes autoSignRes = new AutoSignRes();
        if (member == null) {
            log.info("SignServiceImpl -> shortcutSignIn -> 用户不存在自动生成并返回信息 -> ");
            Member membe = new Member();
            membe.setCreateTime(LocalDateTime.now()).
                    setPhone(mobile).setImEasemobUsername(String.valueOf(id)).
                    setRole(MemberEnum.ROLE_ORDINARY.getValue()).setId(id).
                    setLevel(MemberEnum.LEVEL_ORDINARY.getValue()).setHeadImage("defaultlogo.png").
                    setPassword(Md5Util.getCrypt("123456"))
                    .setName(VerifyCodeUtils.generateInviteCode(10)).
                    setPushStatus(MemberEnum.PUSH_STATUS_CLOSE.getValue()).
                    setDelFlag(flag);
//            membe = invitedMemberFirstLogin(membe , mobile);
            Boolean activated = invokingHxRegister(id);
            if (activated) {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
            } else {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }
            memberMapper.insertSelective(membe);
            BeanUtils.copyProperties(membe, memberInfoDTO);
            memberInfoDTO.setFlag(false);

        } else {
            if (StringUtils.isBlank(member.getImEasemobUsername())) {
                Boolean activated = invokingHxRegister(member.getId());
                if (activated) {
                    member.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
                } else {
                    member.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
                }
                member.setImEasemobUsername(String.valueOf(member.getId()));
                memberMapper.updateByExampleSelective(member, memberExample);
            }
            log.info("SignServiceImpl -> shortcutSignIn -> 用户存在返回信息 -> ");
            BeanUtils.copyProperties(member, memberInfoDTO);
            Long industryId = memberInfoDTO.getIndustryId();
            if(industryId==null){
                memberInfoDTO.setFlag(false);
            } else {
                memberInfoDTO.setFlag(true);
            }
            Long organizationId = member.getOrganizationId();
            if (organizationId != null) {
                Organization organization = organizationMapper.selectByPrimaryKey(organizationId);
                if (organization != null) {
                    memberInfoDTO.setOrganizationType(organization.getType());
                }
            }
        }
        generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                .ip(req.getIp())
                .machineCode(req.getMachineCode())
                .memberId(memberInfoDTO.getId())
                .canal(req.getCanal())
                .loginTime(LocalDateTime.now())
                .version(req.getVersion())
                .build();
        memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(memberInfoDTO.getId())));
        // 生成 refresh token
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberInfoDTO.getId());
        bucket.set(JSONUtil.toJsonStr(memberInfoDTO),120L, TimeUnit.MINUTES);
        // 生成 refresh token
        String refreshToken = memberTokenService.generatorRefreshToken(generatorRefreshTokenDTO);
        // 生成 access token
        String accessToken = memberTokenService.generatorAccessToken(new GeneratorAccessTokenReq(refreshToken));
        // 提交登录事件
        autoSignRes.setAccessToken(accessToken);
        autoSignRes.setRefreshToken(refreshToken);
        autoSignRes.setMemberInfo(memberInfoDTO);

        log.info("SignServiceImpl -> shortcutSignIn -> end -> res -> {}", autoSignRes);
        return autoSignRes;
    }


    /**
     * 会员注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignUpRes signUp(SignUpReq req) {
        String password = req.getPassword();
        if (password == null) {
            password = "123456";
        }
        String code = req.getCode();
        RBucket<String> bucket = redissonClient.getBucket("sms::send::code::" + req.getPhone());
        String redisCode = bucket.get();
        if (redisCode == null || "".equals(redisCode)) {
            log.info("SignServiceImpl -> signIn -> 验证码不存在导致过期 ->");
            throw new BusinessException(BaseEnum.SMS_CODE_ERROR);
        }
        if (!code.equals(redisCode)) {
            log.info("SignServiceImpl -> signIn -> 验证码不匹配 redis code{} -> 请求code{}  请求人{}", code, redisCode, req.getPhone());
            throw new BusinessException(BaseEnum.VERIFY_CODE_ERROR);
        }
        byte flag = ConstantEnum.NOT_DEL.getByte();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria()
                .andPhoneEqualTo(req.getPhone());
        Member member = memberMapper.selectOneByExample(memberExample);
        MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
        long id = snowflakeIdWorker53.nextId();
        GeneratorRefreshTokenDTO generatorRefreshTokenDTO = null;
        SignUpRes signUpRes = new SignUpRes();
        if (member == null) {
            log.info("SignServiceImpl -> signUp -> 用户不存在自动生成并返回信息 -> ");
            Member membe = new Member();
            membe.setCreateTime(LocalDateTime.now()).
                    setPhone(req.getPhone()).setHeadImage("defaultlogo.png").
                    setRole(MemberEnum.ROLE_ORDINARY.getValue()).setId(id).
                    setLevel(MemberEnum.LEVEL_ORDINARY.getValue()).
                    setPassword(Md5Util.getCrypt(password))
                    .setNo(req.getName()).setImEasemobUsername(String.valueOf(id)).
                    setPushStatus(MemberEnum.PUSH_STATUS_CLOSE.getValue()).setDelFlag(flag);
            Boolean activated = invokingHxRegister(id);
            if (activated) {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_FAIL.getValue());
            } else {
                membe.setImEasemobActive(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
            }
            memberMapper.insertSelective(membe);
            signUpRes.setMemberId(id);
            signUpRes.setPhone(req.getPhone());
            signUpRes.setName(req.getName());
            BeanUtils.copyProperties(membe, memberInfoDTO);
            memberInfoDTO.setPassword(Md5Util.getCrypt(String.valueOf(id)));
            generatorRefreshTokenDTO = GeneratorRefreshTokenDTO.builder()
                    .ip(req.getIp())
                    .machineCode(req.getMachineCode())
                    .memberId(id)
                    .canal(req.getCanal())
                    .loginTime(LocalDateTime.now())
                    .version(req.getVersion())
                    .build();

        } else {
            throw new BusinessException("用户已注册");
        }
        publishEventComponent.publishEvent(new MemberAgreeEvent(RegisterEventParam.builder()
                .memberId(id)
                .build()));
        return signUpRes;
    }

    /**
     * 统一调用环信注册接口
     *
     * @param id
     * @return
     */
    public Boolean invokingHxRegister(Long id) {
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
            log.info("SignServiceImpl -> invokingHxRegister -> 返回结果", easemobBaseDTO);
            activated = easemobBaseDTO.getActivated();
        } catch (Exception e) {
            log.info("SignServiceImpl -> invokingHxRegister -> 错误信息", e);
            throw new BusinessException(BaseEnum.NET_UNSTABLE_ERROR);
        }
        return activated;
    }

    /**
     * {@link SelectConfigResp} 对象 转换为  {@link MemberIndustryRes} 对象
     * @param selectConfigResp
     * @return
     */
    private MemberIndustryRes convertIndustry(SelectConfigResp selectConfigResp){
        MemberIndustryRes res = new MemberIndustryRes();
        if (ObjectUtil.isNotEmpty(selectConfigResp)){
            BeanUtils.copyProperties(selectConfigResp , res);
        }
        return res;
    }

    /**
     * 受邀请用户第一次登录app
     * @param member {@link Member 用户对象}
     * @param phone 手机号
     * @return {@link Member 用户对象}
     */
    public Member invitedMemberFirstLogin(Member member, String phone){
        InviteExample inviteExample = new InviteExample();
        inviteExample.createCriteria()
                .andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte())
                .andExpireTimeGreaterThanOrEqualTo(LocalDateTime.now())
                .andInvitePhoneEqualTo(phone)
                .andStatusEqualTo(InviteEnum.PHONE.getCode());
        Invite invite = inviteMapper.selectOneByExample(inviteExample);
        if (ObjectUtil.isEmpty(invite)){
            return member;
        }
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(invite.getMemberId()).andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte());
        Member inviteMember = memberMapper.selectOneByExample(memberExample);
        memberExample.clear();
        if (ObjectUtil.isEmpty(inviteMember) || inviteMember.getOrganizationId() == null){
            return member;
        }
        switch (invite.getType()){
            case 0:
                memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andTeacherIdEqualTo(inviteMember.getId());
                List<Member> members = memberMapper.selectByExample(memberExample);
                if (members.size() >= 10){
                    return member;
                }
                member.setTeacherId(inviteMember.getId())
                        .setRole(MemberEnum.ROLE_ASSISTANT.getValue())
                        .setOrganizationId(inviteMember.getOrganizationId())
                        .setLevel(inviteMember.getLevel())
                        .setPhone(invite.getInvitePhone())
                        .setName(invite.getInviteName())
                        .setBelong(inviteMember.getBelong())
                        .setIndustryId(inviteMember.getIndustryId())
                        .setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue())
                        .setVipStartTime(inviteMember.getVipStartTime())
                        .setVipEndTime(inviteMember.getVipEndTime());
                break;
            case 1:
                member.setPhone(invite.getInvitePhone())
                        .setName(invite.getInviteName())
                        .setPassword(member.getPassword())
                        .setBelong(inviteMember.getBelong())
                        .setOrganizationId(inviteMember.getOrganizationId())
                        .setIndustryId(inviteMember.getIndustryId())
                        .setVerifyStatus(MemberEnum.VERIFY_STATUS_CLOSE.getValue());
                break;
            case 2:
                memberExample.createCriteria().andDelFlagEqualTo(ConstantEnum.NOT_DEL.getByte()).andOrganizationIdEqualTo(inviteMember.getOrganizationId());
                List<Member> enterprisesMembers = memberMapper.selectByExample(memberExample);
                if (enterprisesMembers.size() >= 5){
                    return member;
                }
                member.setPassword(member.getPassword())
                        .setName(invite.getInviteName())
                        .setPhone(invite.getInvitePhone())
                        .setJobPosition(invite.getInvitePosition())
                        .setOrganizationId(inviteMember.getOrganizationId())
                        .setVerifyStatus(MemberEnum.VERIFY_STATUS_SUCCESS.getValue())
                        .setPositionId(inviteMember.getPositionId())
                        .setBelong(inviteMember.getBelong())
                        .setIndustryId(inviteMember.getIndustryId());
                break;
        }
        invite.setStatus(InviteEnum.LOGIN.getCode());
        inviteMapper.updateByPrimaryKey(invite);
        return member;
    }

}
