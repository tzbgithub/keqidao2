package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationAndMemberDto {
    @ApiModelProperty(name = "organizationId", value = "机构标识",required = false)
    private Long organizationId;
    @ApiModelProperty(name = "memberId", value = "用户标识",required = false)
    private Long memberId;
}
