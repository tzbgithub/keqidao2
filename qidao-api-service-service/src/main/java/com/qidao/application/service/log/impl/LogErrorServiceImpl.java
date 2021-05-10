package com.qidao.application.service.log.impl;

import cn.hutool.core.bean.BeanUtil;
import com.qidao.application.entity.log.LogError;
import com.qidao.application.mapper.log.LogErrorMapper;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.log.LogErrorConstantEnum;
import com.qidao.application.model.log.LogErrorInsertBatchReq;
import com.qidao.application.service.log.LogErrorService;
import com.qidao.framework.util.SnowflakeIdWorker53;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 3:06 PM
 */
@Slf4j
@Service
public class LogErrorServiceImpl implements LogErrorService {

    @Resource
    private LogErrorMapper logErrorMapper;

    @Resource
    private SnowflakeIdWorker53 snowflakeIdWorker53;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer insertBatch(List<LogErrorInsertBatchReq.ErrorMessage> errorMessageList) {
        log.info("LogErrorServiceImpl -> insertBatch -> List<LogErrorInsertBatchReq.ErrorMessage> errorMessageList : {}", errorMessageList);
        List<LogError> logErrors = new LinkedList<>();
        for (LogErrorInsertBatchReq.ErrorMessage errorMessage : errorMessageList) {
            LogError logError = new LogError();
            BeanUtil.copyProperties(errorMessage, logError);
            logError.setId(snowflakeIdWorker53.nextId());
            logError.setCreateBy(errorMessage.getMemberId());
            logError.setCreateTime(LocalDateTime.now());
            logError.setStatus(LogErrorConstantEnum.STATUS_COMMITTED.getIntVal());
            logError.setDelFlag(ConstantEnum.NOT_DEL.getByte());
            logErrors.add(logError);
        }
        Integer res = logErrorMapper.batchInsert(logErrors);
        log.info("LogErrorServiceImpl -> insertBatch -> Return -> Integer : {}", res);
        return res;
    }
}
