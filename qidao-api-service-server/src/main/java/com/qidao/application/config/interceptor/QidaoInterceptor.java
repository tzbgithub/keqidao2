package com.qidao.application.config.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.wrapper.QidaoRequestWrapper;
import com.qidao.application.entity.organization.Organization;
import com.qidao.application.mapper.organization.OrganizationMapper;
import com.qidao.application.model.common.QiDaoEncodeUtil;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.member.sign.MemberInfoDTO;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.application.model.organization.OrganizationEnum;
import com.qidao.application.service.redis.MemberRedissonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * API 拦截器
 *
 * @author Autuan.Yu
 */
@Slf4j
public class QidaoInterceptor implements HandlerInterceptor {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private QiDaoEncodeUtil qiDaoEncodeUtil;
    @Resource
    private OrganizationMapper organizationMapper;

    @Value("${token.access.expire}")
    private Integer expireTime;
    @Value("${knife4j.enable}")
    private boolean knife4jEnable;
    @Resource
    private MemberRedissonService memberRedissonService;
    @Resource(name = "checkVersion")
    private CheckVersion checkVersion;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 如果是测试环境 直接通过 ( 待登录接口完善后修改）
        String version = request.getHeader(TokenConstantEnum.HEADER_VERSION.getValue());
        String canal = request.getHeader(TokenConstantEnum.HEADER_CANAL.getValue());
        String machine = request.getHeader(TokenConstantEnum.HEADER_MACHINE.getValue());

        String accessToken = request.getHeader(TokenConstantEnum.ACCESS_TOKEN_NAME.getValue());
        // 暂时只做非空验证: 版本号；渠道；机器码
        if (StrUtil.hasBlank(version, canal, machine)) {
            throw new BusinessException(BaseEnum.VERIFY);
        }
        if (StrUtil.isBlank(accessToken)) {
            log.warn("interceptor -> 未传 TOKEN");
            throw new BusinessException(BaseEnum.TOKEN_EXPIRED);
        }

        RBucket<String> accessTokenBucket = redissonClient.getBucket(accessToken);
        if (!accessTokenBucket.isExists()) {
            log.warn("interceptor -> TOKEN 过期或不存在");
            throw new BusinessException(BaseEnum.TOKEN_EXPIRED);
        }
        //版本号控制
//        checkVersion.checkVersion(version, canal, machine);

        String refreshToken = accessTokenBucket.get();
        String jsonStr = qiDaoEncodeUtil.desDecrypt(refreshToken);
        GeneratorRefreshTokenDTO generatorRefresh = JSONUtil.toBean(jsonStr, GeneratorRefreshTokenDTO.class);
        //拦截冻结权限
        Long memberId = generatorRefresh.getMemberId();
        RBucket<String> bucket = memberRedissonService.getMemberLoginId(memberId);
        MemberInfoDTO memberInfoDto = JSONUtil.toBean(bucket.get(), MemberInfoDTO.class);

        Integer level = memberInfoDto.getLevel();
        if (level == null) {
            throw new BusinessException(BaseEnum.UN_LOGIN_ERROR);
        }
        if (level == -1) {
            log.info("interceptor -> 冻结拦截");
            throw new BusinessException(BaseEnum.ACC_STOP_ERROR);
        }
        // 如果会员是 企业类型
        if (OrganizationEnum.TYPE_COMPANY.getValue().equals(memberInfoDto.getOrganizationType())) {
            Organization organization = organizationMapper.selectByPrimaryKey(memberInfoDto.getOrganizationId());
            // 组织机构拥有vip，视为用户拥有vip权限
            boolean isEnterpriseVip = null != organization.getVipEndTime() && LocalDateTime.now().isBefore(organization.getVipEndTime());
            level = isEnterpriseVip ? MemberEnum.LEVEL_VIP.getValue() : MemberEnum.LEVEL_ORDINARY.getValue();
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod methodHandle = (HandlerMethod) handler;
            QiDaoPermission annotation = methodHandle.getMethodAnnotation(QiDaoPermission.class);
            if (null != annotation) {
                //用户等级判断
                int accessLevel = annotation.accessLevel();
                if (level < accessLevel) {
                    log.info("interceptor -> 权限拦截");
                    throw new BusinessException(BaseEnum.UN_ROOT_ERROR);
                }
                QidaoRequestWrapper requestWrapper = null;
                requestWrapper = (QidaoRequestWrapper) request;
                String body = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());
                if (StrUtil.isBlank(body)) {
                    throw new BusinessException(BaseEnum.UN_ROOT_ERROR);
                }
                JSONObject obj = JSONUtil.parseObj(body);
                Long requestMemberId = getMemberId(obj);
                if (!generatorRefresh.getMemberId().equals(requestMemberId)) {
                    throw new BusinessException(BaseEnum.SERVICE_ROOT_ERROR);
                }

                String ableMember = annotation.ableMember();
                if (StrUtil.isNotBlank(ableMember)) {
                    List<String> ableMemberList = Arrays.asList(ableMember.split(","));
                    if (CollUtil.isNotEmpty(ableMemberList)) {
                        boolean contain = ableMemberList.contains(requestMemberId.toString());
                        if (!contain) {
                            throw new BusinessException(BaseEnum.UN_ROOT_ERROR);
                        }
                    }
                }
            }
        }


        RMap<String, String> refreshTokenMap = redissonClient.getMap(TokenConstantEnum.REFRESH_TOKEN_KEY.getValue());

        refreshTokenMap.put(refreshToken, LocalDateTime.now().format(DatePattern.NORM_DATETIME_FORMATTER));
        accessTokenBucket.expire(expireTime, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    /**
     * 获取请求参数中的 memberId <br/>
     * 如果请求体内包含下列参数，视作 memberId : (列表顺序为优先级） <br/>
     * <ul>
     *     <li>memberId</li>
     *     <li>id</li>
     *     <li>memberIdPartyA</li>
     * </ul>
     *
     * @param obj 请求体 JsonObject 对象
     * @return Long memberId ; null - 不包含 memberId 对象
     */
    private Long getMemberId(JSONObject obj) {
        if (obj.containsKey(ConstantEnum.VAR_MEMBER_ID.getStr())) {
            return obj.getLong(ConstantEnum.VAR_MEMBER_ID.getStr());
        }
        if (obj.containsKey(ConstantEnum.VAR_ID.getStr())) {
            return obj.getLong(ConstantEnum.VAR_ID.getStr());
        }
        if (obj.containsKey(ConstantEnum.VAR_MEMBER_ID_PARTY_A.getStr())) {
            return obj.getLong(ConstantEnum.VAR_MEMBER_ID_PARTY_A.getStr());
        }
        return null;
    }
}
