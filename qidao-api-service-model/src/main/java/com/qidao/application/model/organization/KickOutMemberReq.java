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
@ApiModel("企业管理员删除员工请求对象")
public class KickOutMemberReq {

    @NotNull(message = "管理员Id不能为空")
    @ApiModelProperty(value = "管理员Id")
    private Long adminId;

    @NotNull(message = "员工Id不能为空")
    @ApiModelProperty(value = "员工Id")
    private Long memberId;

}
