package com.qidao.application.model.config;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/16 4:39 PM
 */
@Getter
public enum SelectConfigEnum {

    /**
     * TYPE_ 配置类别 <br/>
     * (以下翻译通过百度百科，避免歧义) <br/>
     * <ul>
     *     <li>0 - 服务需求领域; Service demand</li>
     *     <li>1 - 组织规模大小; Size of organization</li>
     *     <li>2 - 研发规模大小; R & D scale</li>
     *     <li>3 - 研发经费; R & D funds</li>
     *     <li>4 - 学历; education</li>
     *     <li>5 - 职位; position</li>
     *     <li>6 - 行业; industry</li>
     *     <li>7 - 投诉; complaint</li>
     *     <li>8 - 反馈；feedback</li>
     *     <li>9 - 会员领域 Member area</li>
     * </ul>
     *
     * 改用 DictConstantEnum 枚举
     */
    @Deprecated
    TYPE_SERVICE_DEMAND(0),
    @Deprecated
    TYPE_SIZE_OF_ORGANIZATION(1),
    @Deprecated
    TYPE_R_D_SCALE(2),
    @Deprecated
    TYPE_R_D_FUNDS(3),
    @Deprecated
    TYPE_EDUCATION(4),
    @Deprecated
    TYPE_POSITION(5),
    @Deprecated
    TYPE_INDUSTRY(6),
    @Deprecated
    TYPE_COMPLAINT(7),
    @Deprecated
    TYPE_FEEDBACK(8),
    @Deprecated
    TYPE_MEMBER_AREA(9),
    @Deprecated
    TYPE_MEMBER_PROFESSIONAL(10),

    /**
     * STATUS_ 激活状态 0: 正常(默认) 1: 停用
     */
    STATUS_ACTIVE(0),
    STATUS_INACTIVE(1),
    /**无父节点**/
    NOT_PID(0),
    ;

    private final Integer value;

    SelectConfigEnum(Integer value) {
        this.value = value;
    }

    public Long getLong(){
        return Long.parseLong(value.toString());
    }
}
