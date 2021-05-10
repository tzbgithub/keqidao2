package com.qidao.application.model.server;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 1:43 PM
 */
@Getter
public enum ServerEnum {

    /**
     * DELETE_FLAG_ 逻辑删除: 0 否(默认)， 1 是
     * <p>
     * STATUS_ 需求状态：0-草稿 1-已发布 2-已接受 3-已取消
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),
    TEXT_TYPE(-1),
    IMAGE_TYPE(0),
    VIDEO_TYPE(1),

    STATUS_DRAFT(0),
    STATUS_RELEASED(1),
    STATUS_ACCEPTED(2),
    STATUS_CANCELED(3);

    private final int value;

    ServerEnum(int value) {
        this.value = value;
    }
}
