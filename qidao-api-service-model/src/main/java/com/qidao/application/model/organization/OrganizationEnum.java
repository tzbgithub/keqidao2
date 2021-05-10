package com.qidao.application.model.organization;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/17 8:58 AM
 */
@Getter
public enum OrganizationEnum {

    /**
     * TYPE_ 组织机构类别 <br />
     * <ul>
     *     <li>0 实验室</li>
     *     <li>1 公司</li>
     * </ul>
     */
    TYPE_LABORATORY(0),
    TYPE_COMPANY(1),

    /**
     * STATUS_ 认证状态 <br />
     * <ul>
     *     <li>0 未认证(默认)</li>
     *     <li>1 认证中</li>
     *     <li>2 已认证</li>
     * </ul>
     */
    STATUS_UNAUTHORIZED(0),
    STATUS_AUTHORIZING(1),
    STATUS_AUTHORIZED(2),

    /**
     * VERIFY_STATUS_ 审核状态 <br />
     * <ul>
     *     <li>0 未审核(默认)</li>
     *     <li>1 审核中</li>
     *     <li>2 审核后</li>
     * </ul>
     */
    VERIFY_STATUS_UNVERIFIED(0),
    VERIFY_STATUS_VERIFYING(1),
    VERIFY_STATUS_VERIFIED(2),

    /**
     * DELETE_FLAG_ 逻辑删除 <br />
     * <ul>
     *     <li>0 否(默认)</li>
     *     <li>1 是</li>
     * </ul>
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1);

    private final Integer value;

    OrganizationEnum(int value) {
        this.value = value;
    }
}
