package com.qidao.application.model.member.feedback;

import lombok.Getter;

@Getter
public enum FeedbackEnum {
    /**
     *
     * DELETE_FLAG_ 删除标志： 0-未删除 1-已删除
     *
     * STATUS_ 处理状态：0-未处理 1-处理中 2-已处理
     *
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),

    STATUS_PENDING(0),
    STATUS_PROCESSING(1),
    STATUS_SOLVED(2);

    private final int value;

    FeedbackEnum(int value) {
        this.value = value;
    }
}
