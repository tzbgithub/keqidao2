package com.qidao.application.model.member.subscribe;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class AttentionOrganizationReq {


    @NotNull
    @ApiModelProperty(name = "memberId", value = "当前会员的用户标识",required = true,example = "130879657672706")
    private Long memberId;
    @NotNull
    @ApiModelProperty(name = "delFlag", value = "0关注；1取消关注 关注以后再次点击为取消关注 传入类型不可一致",required = true,example = "0")
    private  byte delFlag;
    @NotNull
    @ApiModelProperty(name = "subscribeId", value = "被关注的组织机构标识",required = true,example = "130899165380610")
    private Long subscribeId;
    @NotNull
    @ApiModelProperty(name = "subscribeName", value = "被关注的对象名称",required = true,example = "130899165380610")
    private String subscribeName;

}
