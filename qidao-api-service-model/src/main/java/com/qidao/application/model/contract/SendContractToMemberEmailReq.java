package com.qidao.application.model.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SendContractToMemberEmailReq {
    @ApiModelProperty(value = "当前登录会员ID",required = true)
    @NotNull(message = "会员ID 不能为空")
    @Min(value = 1L,message = "会员ID 不合法")
    private Long memberId;

    @ApiModelProperty(value = "需要发送的合同ID",required = true)
    @NotNull(message = "合同ID 不能为空")
    @Min(value = 1L,message = "合同ID 不合法")
    private Long contractId;
}
