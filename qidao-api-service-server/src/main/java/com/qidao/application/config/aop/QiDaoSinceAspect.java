package com.qidao.application.config.aop;

import com.qidao.application.config.enums.SinceCanalEnum;
import com.qidao.application.model.common.VerifyMatch;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.config.enums.QiDaoSinceErrorEnum;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.member.token.TokenConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class QiDaoSinceAspect {

    /**
     * 切入点配置
     *
     * @author Autuan.Yu
     */
    @Pointcut("@annotation(com.qidao.application.config.aop.QiDaoSince)")
    public void aspectLocation() {

    }


    @Before("aspectLocation()")
    public void before(JoinPoint joinPoint){
        QiDaoSince qiDaoSince = getAnnotationLog(joinPoint);
        if(null == qiDaoSince) {
            return;
        }
        SinceCanalEnum canal = qiDaoSince.canal();
        String markVersion = qiDaoSince.value();
        String markEndSupportVersion = qiDaoSince.endSupport();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String currentVersion = request.getHeader(TokenConstantEnum.HEADER_VERSION.getValue());
        // 渠道验证
        if(! SinceCanalEnum.ALL.equals(canal)) {
            String requestCanal = request.getHeader(TokenConstantEnum.HEADER_CANAL.getValue());
            if(! canal.getValue().equals(requestCanal)) {
                throw new BusinessException(QiDaoSinceErrorEnum.CANAL);
            }
        }
        // 开始支持版本
        boolean isy = VerifyMatch.containsAll(currentVersion);
        if(!isy){
            log.info(" QiDaoSinceAspect -> before 版本号格式不正确");
            throw new BusinessException(QiDaoSinceErrorEnum.VERSION_FORMAT);
        }
        boolean flag = VerifyMatch.isLetterDigitOrChinese(currentVersion);
        if(!flag){
            log.info(" QiDaoSinceAspect -> before 版本号格式不正确");
            throw new BusinessException(QiDaoSinceErrorEnum.VERSION_FORMAT);
        }
        int size = compareVersion(currentVersion, markVersion);
        if(size==-1){
            log.info(" QiDaoSinceAspect -> before 当前版本{}比最低支持版本{}要低",currentVersion,markVersion);
            throw new BusinessException(QiDaoSinceErrorEnum.VERSION);
        }
        int endSize = compareVersion(currentVersion, markEndSupportVersion);
        if(endSize==1){
            log.info(" QiDaoSinceAspect -> before 当前版本{}比最后支持版本{}要高",currentVersion,markEndSupportVersion);
            throw new BusinessException(QiDaoSinceErrorEnum.VERSION_END);

        }
        // todo 版本号校验规则：1.0.1 < 0.0.102 修改 (???)[1.31]
        if(! SinceCanalEnum.DEFAULT_VERSION.getValue().equals(markVersion)) {
            Integer markVersionInt = Integer.valueOf(markVersion.replace(ConstantEnum.SPLIT_STR_DOT.getStr(),ConstantEnum.SPLIT_STR_DOT.getStr()));
            if(markVersionInt > Integer.parseInt(currentVersion.replace(ConstantEnum.SPLIT_STR_DOT.getStr(),ConstantEnum.SPLIT_STR_DOT.getStr()))) {
                throw new BusinessException(QiDaoSinceErrorEnum.VERSION);
            }
        }
        if(! ConstantEnum.SPLIT_STR_DOT.getStr().equals(markEndSupportVersion)){
            Integer markVersionInt = Integer.valueOf(markVersion.replace(ConstantEnum.SPLIT_STR_DOT.getStr(),ConstantEnum.SPLIT_STR_DOT.getStr()));
            if(markVersionInt < Integer.parseInt(currentVersion.replace(ConstantEnum.SPLIT_STR_DOT.getStr(),ConstantEnum.SPLIT_STR_DOT.getStr()))) {
                throw new BusinessException(QiDaoSinceErrorEnum.VERSION_END);
            }
        }

    }
    /**
     * 是否存在注解，如果存在就获取
     */
    private QiDaoSince getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(QiDaoSince.class);
        }
        return null;
    }


    /**
     * 版本号比较
     * @param v1
     * @param v2
     * @return 0代表相等，1代表左边大，-1代表右边大
     */
    public static int compareVersion(String v1, String v2) {
        if (v1.equals(v2)) {
            return 0;
        }
        String[] version1Array = v1.split("[._]");
        String[] version2Array = v2.split("[._]");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        long diff = 0;

        while (index < minLen
                && (diff = Long.parseLong(version1Array[index])
                - Long.parseLong(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Long.parseLong(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Long.parseLong(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
}
