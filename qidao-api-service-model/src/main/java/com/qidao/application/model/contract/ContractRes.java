package com.qidao.application.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 11:28 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractRes", description = "[响应]合同")
public class ContractRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "合同id")
    private Long id;
}
