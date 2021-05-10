package com.qidao.application.model.member;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/17 9:01 AM
 */
@Getter
public enum MemberEnum {
    /**
     * LEVEL_ 会员等级 <br />
     * <ul>
     *     <li>-1 冻结权限</li>
     *     <li>0 普通会员(默认)</li>
     *     <li>1 vip会员</li>
     * </ul>
     */
    LEVEL_FREEZE(-1),
    LEVEL_ORDINARY(0),
    LEVEL_VIP(1),

    /**
     * ROLE_ 角色：<br/>
     * <ul>
     *     <li>0 普通用户(默认)</li>
     *     <li>1 主管 </li>
     *     <li>2 管理员</li>
     *     <li>3 行政人员 </li>
     *     <li>4 助手</li>
     * </ul>
     */
    ROLE_ORDINARY(0),
    ROLE_DIRECTOR(1),
    ROLE_ADMINISTRATOR(2),
    ROLE_ADMINISTRATIVE(3),
    ROLE_ASSISTANT(4),

    /**
     * <p>PUSH_STATUS_ 推送开关：</p>
     * <ul>
     *     <li>0 关 </li>
     *     <li>1 开(默认)</li>
     * </ul>
     */
    PUSH_STATUS_CLOSE(0),
    PUSH_STATUS_OPEN(1),

    /**
     * <p>审核状态</p>
     * <ul>
     *     <li>0-未审核</li>
     *     <li>1-审核失败</li>
     *     <li>2-审核成功</li>
     *     <li>3-审核中</li>
     * </ul>
     */
    VERIFY_STATUS_CLOSE(0),
    VERIFY_STATUS_FAIL(1),
    VERIFY_STATUS_SUCCESS(2),
    VERIFY_STATUS_ING(3),

    ;

    private final Integer value;

    MemberEnum(int value) {
        this.value = value;
    }
}
