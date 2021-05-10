package com.qidao.application.config.aop;

import com.qidao.application.entity.config.LogOper;
import com.qidao.application.mapper.config.LogOperMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AsyncOperLogMapper {
    @Resource
    private LogOperMapper logOperMapper;

    @Async
    public Integer asyncSaveLogOper(LogOper logOper) {
        return logOperMapper.insertSelective(logOper);
    }
}
