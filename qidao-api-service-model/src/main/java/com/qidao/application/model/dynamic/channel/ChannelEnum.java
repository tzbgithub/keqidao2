package com.qidao.application.model.dynamic.channel;

import lombok.Getter;

@Getter
public enum ChannelEnum {

    /**
     * DELETE_FLAG_ 删除标志 0-未删除 1-已删除
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1)
    ;

    private final int value;

    ChannelEnum(int value) {
        this.value = value;
    }
}
