package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertiseReq {
    @ApiModelProperty(name = "location", value = "广告位置", example = "1")
    private  Long location;

    // todo channel
    @ApiModelProperty(name = "cancal", value = "渠道号", example = "android")
    private  Long cancal;
}
