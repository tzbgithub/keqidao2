package com.qidao.application.service.log;

import com.qidao.application.model.log.LogErrorInsertBatchReq;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 3:04 PM
 */
public interface LogErrorService {
    /**
     * 批量插入(异常、崩溃信息)
     * @param errorMessageList 需要批量插入的异常/崩溃信息
     * @return 插入条目
     */
    public Integer insertBatch(List<LogErrorInsertBatchReq.ErrorMessage> errorMessageList);
}
