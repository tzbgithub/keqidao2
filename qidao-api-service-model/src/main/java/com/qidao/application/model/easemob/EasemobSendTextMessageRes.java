package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/23 4:36 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobSendTextMessageRes", description = "[响应]发送文本/透传消息")
public class EasemobSendTextMessageRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "respList", value = "接收消息结果集(对象、是否成功发送)")
    private List<EasemobSendTextMessageRes.Resp> respList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Resp {

        @ApiModelProperty(name = "to", value = "接收消息的对象ID(用户、群组、聊天室)", example = "139712941588481")
        private String to;

        @ApiModelProperty(name = "success", value = "表示消息发送成功", example = "true")
        private Boolean success;
    }
}
