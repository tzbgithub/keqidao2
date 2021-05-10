package com.qidao.application.model.contract.progress;

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
@ApiModel(value = "ProgressComfirmReq", description = "[请求]乙方-确认合同的项目周期目标")
public class ProgressComfirmReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(name = "memberId", value = "乙方的会员用户id", required = true, example = "130879657672706")
    private Long memberId;

    @NotNull
    @ApiModelProperty(name = "contractId", value = "未确认项目周期任务的合同id", required = true, example = "4")
    private Long contractId;
}
