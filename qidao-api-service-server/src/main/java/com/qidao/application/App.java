package com.qidao.application;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.qidao.framework.config.*;
import com.qidao.framework.util.FdfsClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableCaching
@EnableApolloConfig
@Import({CaffeineCacheConfig.class
        , Knife4jConfig.class
        , RedissonConfig.class
        , SnowflakeIdWorkerConfig.class
        , FdfsConfiguration.class
        , FdfsClientWrapper.class
        , DateFormatConfig.class
        , LocalDateTimeSerializerConfig.class})
@MapperScan("com.qidao.application.mapper")
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@Slf4j
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("==== 企岛 qidao-api-service  启动成功 ====");
    }
}