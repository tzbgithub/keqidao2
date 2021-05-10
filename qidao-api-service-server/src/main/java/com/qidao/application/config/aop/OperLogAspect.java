package com.qidao.application.config.aop;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qidao.application.config.enums.BusinessStatus;
import com.qidao.application.entity.config.LogOper;
import com.qidao.application.mapper.config.LogOperMapper;
import com.qidao.application.model.common.AddressUtils;
import com.qidao.application.model.common.enums.BaseEnum;
import com.qidao.application.model.member.aspect.AspectConstantEnum;
import com.qidao.application.model.member.token.TokenConstantEnum;
import com.qidao.framework.util.SnowflakeIdWorker53;
import com.qidao.framework.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * OperLogAspect 操作日志切面
 *
 * @author: Autuan.Yu
 * @create: 2020/09/30 16:34
 * @copyright: Toplist
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect {
    @Resource
    private LogOperMapper logOperMapper;
    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker;
    @Resource
    private AsyncOperLogMapper asyncOperLogMapper;

    /**
     * 切入点配置
     *
     * @author Autuan.Yu
     */
    @Pointcut("@annotation(com.qidao.application.config.aop.OperLog)")
    public void aspectLocation() {

    }

    /**
     * 保存日志
     *
     * @param
     * @return
     * @author Autuan.Yu
     */
    @AfterReturning(pointcut = "aspectLocation()", returning = "jsonResult")
    public void afterReturning(JoinPoint joinPoint, Object jsonResult) {
        saveLog(joinPoint, null, jsonResult);
    }

    @AfterThrowing(value = "aspectLocation()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        saveLog(joinPoint, e, null);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }

    /**
     * 保存日志
     *
     * @param joinPoint  切点
     * @param exception  异常
     * @param jsonResult 返回结果 ： ResponseResult
     */
    private void saveLog(final JoinPoint joinPoint, final Exception exception, Object jsonResult) {
        try {
            // 获得注解
            OperLog log = getAnnotationLog(joinPoint);
            if (log == null) {
                return;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Object logMemberIdObj = request.getAttribute(AspectConstantEnum.LOG_MEMBER_ID.getValue());
            Long logMemberId = null;
            if (logMemberIdObj instanceof Long) {
                logMemberId = (Long) logMemberIdObj;
            }

            //JSONUtil.parseObj(JSONUtil.toJsonStr(jsonResult));
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            // 基类获取
            Object[] args = joinPoint.getArgs();
            String operParam = null, unionId = null;
            if (log.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                Object arg = args[0];
                String param = JSONUtil.toJsonStr(arg);
                JSONObject jsonObject = JSONUtil.parseObj(param);
                operParam = StringUtils.substring(param, 0, AspectConstantEnum.LOG_MAX_LENGTH.getInt());
                unionId = jsonObject.getStr(AspectConstantEnum.UNION_ID.getValue());
                logMemberId = jsonObject.getLong(AspectConstantEnum.MEMBER_ID.getValue());
            }
            // 保存数据库
            String ip = ServletUtil.getClientIP(request);

            String canal = request.getHeader(TokenConstantEnum.HEADER_CANAL.getValue());
            String version = request.getHeader(TokenConstantEnum.HEADER_VERSION.getValue());
            LogOper logOper = LogOper.builder()
                    .id(snowflakeIdWorker.nextId())
                    .title(log.title())
                    .businessType(log.businessType().ordinal())
                    .method(className + "." + methodName + "()")
                    .requestMethod(request.getMethod())
                    // 目前第三方只有 微信
                    .thirdType(AspectConstantEnum.WECHAT.getInt())
                    .thirdId(unionId)
                    .operUrl(request.getRequestURI())
                    .operIp(ip)
                    // AddressUtils 不稳定，取消对此ip的检查
//                    .operLocation(AddressUtils.getRealAddressByIP(ip))
                    .operParam(operParam)
                    .jsonResult(StringUtils.substring(JSONUtil.toJsonStr(jsonResult), 0, AspectConstantEnum.LOG_MAX_LENGTH.getInt()))
                    .remark(log.remark())
                    .version(version)
                    .operTime(LocalDateTime.now())
                    .operUserId(logMemberId)
                    .operatorType(AspectConstantEnum.switchOperType(canal))
                    .build();

            if (exception != null) {
                logOper.setStatus(BusinessStatus.FAIL.ordinal());
                logOper.setErrorMsg(StringUtils.substring(exception.getMessage(), 0, AspectConstantEnum.LOG_MAX_LENGTH.getInt()));
            } else if (jsonResult instanceof ResponseResult) {
                ResponseResult result = (ResponseResult) jsonResult;
                if (!BaseEnum.SUCCESS.getCode().equals(result.getCode())) {
                    logOper.setStatus(BusinessStatus.FAIL.ordinal());
                    logOper.setErrorMsg(StringUtils.substring(result.getMessage(), 0, AspectConstantEnum.LOG_MAX_LENGTH.getInt()));
                }
            }
            if (log.isAsyncSaveToDB()) {
                asyncOperLogMapper.asyncSaveLogOper(logOper);
            } else {
                logOperMapper.insertSelective(logOper);
            }
        } catch (Exception e) {
            log.error("OperLogAspect -> saveLog -> error ", e);
        }
    }

}
