package com.qidao.application.model.invoice;

import lombok.Getter;

@Getter
public enum InvoiceEnum {

    /**
     * LEVEL_ 会员等级： 0-普通会员(默认) 1-vip会员
     *
     * STATUS_ 处理状态： 0-申请中 1-已处理
     *
     * DELETE_FLAG_ 逻辑删除： 0-否(默认) 1-是
     */

    LEVEL_ORDINARY(0),
    LEVEL_VIP(1),

    STATUS_ING_PROCESS(0),
    STATUS_ED_PROCESS(1),

    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1);

    private final int value;

    InvoiceEnum(int value) {
        this.value = value;
    }
}
