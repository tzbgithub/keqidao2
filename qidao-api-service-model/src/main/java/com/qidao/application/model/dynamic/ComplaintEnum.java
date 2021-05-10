package com.qidao.application.model.dynamic;


import lombok.Getter;

@Getter
public enum ComplaintEnum {

    /**
     *
     * DELETE_FLAG_ 逻辑删除标志 0-未删除 1-已删除
     *
     * PROCESS_STATUS_ 投诉处理状态 0-未处理 1-处理中 2-已处理
     *
     */

    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),

    PROCESS_STATUS_UN(0),
    PROCESS_STATUS_ING(1),
    PROCESS_STATUS_ED(2),

    TYPE_DYNAMIC(0),
    TYPE_MEMBER(1),
    ;
    private final Integer value;

    ComplaintEnum(int value){
        this.value = value;
    }
}
