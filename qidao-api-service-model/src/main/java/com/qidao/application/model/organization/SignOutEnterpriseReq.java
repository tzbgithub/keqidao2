package com.qidao.application.model.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("退出企业请求对象")
public class SignOutEnterpriseReq {

    @NotNull(message = "会员ID不呢为空")
    @ApiModelProperty(value = "会员ID" , required = true , example = "123")
    private Long memberId;

    @ApiModelProperty(value = "目标用户ID"  , example = "666")
    private Long sourceId;

    @ApiModelProperty(value = "组织机构ID" , required = true , example = "142432905789442")
    private Long organizationId;

}
