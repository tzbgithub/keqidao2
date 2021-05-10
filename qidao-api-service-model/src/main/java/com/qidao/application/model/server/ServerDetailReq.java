package com.qidao.application.model.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/14 1:16 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ServerDetailReq", description = "[请求]获取需求详情页")
public class ServerDetailReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiParam(name = "serverId", value = "服务需求id", required = true, example = "1")
    @NotNull
    private Long serverId;


}