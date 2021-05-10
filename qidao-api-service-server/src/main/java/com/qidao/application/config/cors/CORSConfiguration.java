package com.qidao.application.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
//                        .allowedOriginPatterns("*")
                        .allowedOriginPatterns(
                                "http://192.168.0.22",
                                "http://192.168.0.22/**",
                                "*.autuan.top",
                                "*.autuan.top/**",
                                "*.keqidao.com",
                                "*.keqidao.com/**"
                        )
                ;
            }
        };
    }
}