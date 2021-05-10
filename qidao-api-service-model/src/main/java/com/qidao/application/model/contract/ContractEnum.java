package com.qidao.application.model.contract;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 1:43 PM
 */
@Getter
public enum ContractEnum {

    /**
     * DELETE_FLAG_ 逻辑删除: 0 否(默认)， 1 是
     * <p>
     * STATUS_ 合同状态：0-草稿 1-已确定合同(进度已确定) 2-已签订合同(合同生效) 3-进度已完成
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),

    STATUS_DRAFT(0),
    STATUS_CONFIRMED(1),
    STATUS_SIGNED(2),
    STATUS_COMPLETED(3);

    private final int value;

    ContractEnum(int value) {
        this.value = value;
    }
}
