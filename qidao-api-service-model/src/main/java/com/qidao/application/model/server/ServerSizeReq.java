package com.qidao.application.model.server;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("需求数量请求对象")
public class ServerSizeReq {

    @ApiModelProperty(value = "用户ID" , example = "123")
    private Long memberId;

}
