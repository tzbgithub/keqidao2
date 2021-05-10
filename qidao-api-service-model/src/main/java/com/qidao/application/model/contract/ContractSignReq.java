package com.qidao.application.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 1:50 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractSignReq", description = "[请求]乙方-签订合同")
public class ContractSignReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(name = "memberId", value = "乙方的会员用户id", required = true, example = "130879657672706")
    private Long memberId;

    @NotNull
    @ApiModelProperty(name = "contractId", value = "已确认项目待乙方签订的合同id", required = true, example = "5")
    private Long contractId;
}
