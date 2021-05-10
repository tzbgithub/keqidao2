package com.qidao.application.model.contract.progress;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/15 2:09 PM
 */
@Getter
public enum ProgressEnum {
    /**
     * DELETE_FLAG_ 逻辑删除: 0 否(默认)， 1 是
     * <p>
     * STATUS_ 进度状态: 0-未确认 1-已确认待完成 2-已完成待验收 3-已验收 4-超时完成
     * <p>
     * STATUS_A_ 甲方显示的进度状态：0-待确认 1-执行中 2-待验收 3-已验收 4-超时完成
     * <p>
     * STATUS_B_ 乙方显示的进度状态：0-待确认 1-未完成 2-已完成待验收 3-已验收 4-超时完成
     * <p>
     * ROLE_ 甲方或乙方: 0-甲方 1-乙方
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),

    STATUS_UNCONFIRMED(0),
    STATUS_EXECUTING(1),
    STATUS_COMPLETED(2),
    STATUS_ACCEPTED(3),
    STATUS_OVERTIME(4),

    TITLE_A_UNCONFIRMED("待确认"),
    TITLE_A_EXECUTING("执行中"),
    TITLE_A_COMPLETED("待验收"),
    TITLE_A_ACCEPTED("已验收"),
    TITLE_A_OVERTIME("超时完成待验收"),

    TITLE_B_UNCONFIRMED("待确认"),
    TITLE_B_EXECUTING("未完成"),
    TITLE_B_COMPLETED("已完成待验收"),
    TITLE_B_ACCEPTED("已验收"),
    TITLE_B_OVERTIME("超时完成待验收"),

    TITLE_ERROR("异常"),

    ROLE_A(0),
    ROLE_B(1);

    private final int intValue;

    private final String strValue;

    ProgressEnum(int value) {
        this.intValue = value;
        strValue = null;
    }

    ProgressEnum(String strValue) {
        this.intValue = Integer.MIN_VALUE;
        this.strValue = strValue;
    }
}
