package com.qidao.application.service.contract;

import com.qidao.application.model.contract.progress.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 11:20 AM
 */
public interface ProgressService {
    /**
     * 乙方-完成项目中的某项进度任务
     */
    ProgressDTO complete(ProgressCompleteReq req);

    /**
     * 甲方-验收项目中某项"已完成"的进度任务
     */
    ProgressDTO accept(ProgressAcceptReq req);

    /**
     * 获取项目进度详情页
     */
    ProgressDTO detail(ProgressDetailReq req);

    /**
     * 获取项目周期目标列表项(待确认)
     */
    ProgressDTO listSteps(ProgressListStepsReq req);

    /**
     * 乙方-确认甲方设定的项目周期目标
     */
    ProgressDTO confirm(ProgressComfirmReq req);
}
