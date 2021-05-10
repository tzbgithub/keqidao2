package com.qidao.application.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截器配置
 *
 * @author Autuan.Yu
 */
@Configuration
public class QidaoInterceptorConfig implements WebMvcConfigurer {
    /**
     * 获取不拦截的路径
     *
     * @return 路径集合
     */
    private List<String> excludePathList() {
        return Arrays.asList(
                // 获取token
                "/member/token/getToken",
                // 登录注册
                "/sms/login/send",
                "/member/sign/**",
                // 支付回调通知
                "/pay/wechat/app/callback",
                "/pay/ali/callback",
                // 微信公众号 事件推送接收
                "/wechat/official/event",
                // 注册绑定行业
                "/config/getSelectFatherSonByType",
                "/member/memberBindingIndustry",
                // 微信小程序免登录
                "/mp/**",
                // pc官网需要解除登录
                "/dynamic/getRandomDynamic",
                // 分享 H5 等
                "/dynamic/getDynamicDetailed",
                // 获取版本号
                "/config/canal/verificationVersion",
                // swagger 文档
                "/webjars/**",
                "/doc.html",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                //邀请 H5
                "/invite/backInviteMemberInfo",
                "/invite/insertInvite",
                "/invite/verificationCode",
                "/invite/perfectInformation"
        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(qidaoInterceptor())
                // 排除:不拦截的路径
                .excludePathPatterns(excludePathList());
    }

    @Bean
    QidaoInterceptor qidaoInterceptor() {
        return new QidaoInterceptor();
    }
}
