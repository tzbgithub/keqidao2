package com.qidao.application.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员修改事件参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateEventParam {
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 用户名称
     */
    private String memberName;
    /**
     * 用户图像
     */
    private String memberHeadImg;
    /**
     * 用户职位
     */
    private String memberPosition;
    /**
     * 所属机构名称
     */
    private String memberOrganizationName;
    /**
     *身份 0-会员 1-组织机构 2-实验室
     */
    private Integer memberType;
    /**
     * 学历
     */
    private String education;
    /**
     * 所属单位
     */
    private String belong;


    private MemberUpdateEventEnum memberUpdateEnum;


    public enum MemberUpdateEventEnum{
        /**
         * 创建组织机构
         */
        CREATE_ORGANIZATION(4),
        /**
         * 注销
         */
        CANCELLATION_MEMBER(3),
        /**
         * 修改组织
         */
        UPDATE_ORGANIZATION(2),
        /**
         * 修改会员信息
         */
        UPDATE(1),
        /**
         * 修改用户个人信息
         */
        UPDATE_MEMBER_MESSAGE(0);

        private Integer eventType;
        MemberUpdateEventEnum(Integer eventType){
            this.eventType = eventType;
        }
    }
}
