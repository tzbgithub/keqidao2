package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateCompanyReq {
    @ApiModelProperty(name = "memberId", value = "用户标识")
    private Long memberId;
//    @ApiModelProperty(name = "responsibleMemberId", value = "负责人标识")
//    private Long responsibleMemberId;
//    @ApiModelProperty(name = "name", value = "规模id")
//    private String name;
//    @ApiModelProperty(name = "industryId", value = "所属行业标识")
//    private Long industryId;
}
