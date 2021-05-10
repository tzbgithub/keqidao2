package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/25 2:09 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeUpdateAllReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 当前会员的用户id
     */
    @JsonIgnore
    private Long subscribeId;

    /**
     * 关注对象头像
     */
    @JsonIgnore
    private String subscribeHeadImg;

    /**
     * 关注对象名称
     */
    @JsonIgnore
    private String subscribeName;

    /**
     * 关注对象职位
     */
    @JsonIgnore
    private String subscribePosition;

    /**
     * 关注对象所属机构名称
     */
    @JsonIgnore
    private String subscribeOrganizationName;

    /**
     * 关注对象的学历
     */
    private String subscribeEducation;

    /**
     * 关注对象的所属单位
     */
    private String subscribeBelong;
}