package com.qidao.application.service.contract;

import com.qidao.application.model.contract.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 11:20 AM
 */
public interface ContractService {
    /**
     * 获取指定用户的合同(项目)列表
     */
    ContractDTO list(ContractListReq req);

    /**
     * 乙方-签订合同
     */
    ContractDTO sign(ContractSignReq req);

    /**
     * 发送合同到邮箱
     * @param req
     */
    void sendContractToMemberEmail(SendContractToMemberEmailReq req);

    /**
     * 获取合同的基本信息
     * @param id 合同ID
     * @return
     */
    ContractBaseInfoRes getContractBaseInfo(Long id);
}
