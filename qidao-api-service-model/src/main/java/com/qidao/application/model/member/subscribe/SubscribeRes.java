package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/16 9:17 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SubscribeRes", description = "[响应]关注or屏蔽(SubscribeRes),会员A关注会员B，或者屏蔽会员B")
public class SubscribeRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "subscribeId", value = "被关注/屏蔽的对象的用户id/组织id")
    private Long subscribeId;

    @ApiModelProperty(name = "subscribeHeadImg", value = "被关注/屏蔽的用户的头像")
    private String subscribeHeadImg;

    @ApiModelProperty(name = "subscribeName", value = "被关注/屏蔽的用户的名称")
    private String subscribeName;

    @ApiModelProperty(name = "subscribeEducation", value = "被关注/屏蔽的用户的学历")
    private String subscribeEducation;

    @ApiModelProperty(name = "subscribePosition", value = "被关注/屏蔽的用户的职位")
    private String subscribePosition;

    @ApiModelProperty(name = "subscribeOrganizationName", value = "被关注/屏蔽的对象的所属机构名称")
    private String subscribeOrganizationName;

    @ApiModelProperty(name = "subscribeBelong", value = "被关注/屏蔽的用户的所属单位名称")
    private String subscribeBelong;
}
