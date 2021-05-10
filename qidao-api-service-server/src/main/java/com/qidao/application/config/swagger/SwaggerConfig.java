package com.qidao.application.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;


/**
 * @author Autuan.Yu
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer  {
    @Bean
    Docket favor(){
        return registerGroup("收藏","收藏相关接口","/member/favor/**");
    }
    @Bean
    Docket dynamic(){
        return registerGroup("动态","动态相关接口","/dynamic/**","/comment/**");
    }
    @Bean
    Docket memberExample(){ return registerGroup("会员管理","会员管理","/member/**"); }
    @Bean
    Docket organizationExample(){ return registerGroup("组织机构","组织机构","/organization/**","/enterprise/**"); }
    @Bean
    Docket achievementExample(){
        return registerGroup("App成果模块","App成果模块","/achievementEquipment/**");
    }
    @Bean
    Docket subscribeExample(){ return registerGroup("关注or屏蔽","关注or屏蔽","/member/subscribe/**"); }
//    @Bean
//    Docket comment(){
//        return registerGroup("动态评论","动态评论相关接口","/comment/**");
//    }
    @Bean
    Docket order(){
        return registerGroup("付费相关接口","订单、支付、产品相关接口","/order/**","/pay/**","/product/**");
    }
    @Bean
    Docket miniProgram(){return registerGroup("小程序","小程序接口","/mp/**");}
    @Bean
    Docket serverExample(){
        return registerGroup("App需求","App需求","/server/**");
    }
    @Bean
    Docket msg(){return registerGroup("消息","消息接口","/msg/**");}
    @Bean
    Docket contract(){return registerGroup("合同","合同相关接口","/contract/**");}
    @Bean
    Docket server(){return registerGroup("需求","需求相关接口","/server/**");}
    @Bean
    Docket behave(){return registerGroup("App足迹","App足迹","/behave/**");};
    @Bean
    Docket recommend(){return registerGroup("推荐","千人千面推荐","/recommend/**");}
    @Bean
    Docket log(){return registerGroup("日志相关","日志相关","/log/**");}
    @Bean
    Docket advertise(){return registerGroup("广告栏位","广告栏位","/advertise/**");}
    @Bean
    Docket im(){return registerGroup("环信IM","环信IM","/easemob/api/**");}
    @Bean
    Docket canal(){return registerGroup("版本號","版本號","/config/canal/**");}
    @Bean
    Docket invite(){return registerGroup("邀请","邀请","/invite/**");}
    @Bean
    Docket other(){return registerGroup("功能辅助接口","各模块都有可能用到的接口",
            "/address/**","/upload/**","/config/**");}

    /**
     * 放行 swagger 相关路径
     * @param registry
     * @author Autuan.Yu
     * @date 2020.12.15
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 返回 swagger 分组对象
     * @param title 标题
     * @param description 组描述
     * @param pathArray 匹配路径，可以多个
     * @return Docket swagger docket 对象
     * @author Autuan.Yu
     * @date 2020.12.15
     */
    private Docket registerGroup(String title,String description,String... pathArray){
        Predicate<String> paths = null;
        for(String path : pathArray) {
            if(null == paths ) {
                paths = PathSelectors.ant(path);
            } else {
                paths = paths.or(PathSelectors.ant(path));
            }
        }
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName(title)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .build())
                .select()
                .paths(paths)
                .build();
    }
}
