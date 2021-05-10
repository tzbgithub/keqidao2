package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "EasemobSendTextMessageReq", description = "[请求]发送文本/透传消息")
public class EasemobSendTextMessageReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "target_type,发送的目标类型,不能为空")
    @ApiModelProperty(name = "target_type", value = "发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息", required = true, example = "users")
    private String target_type;

    @Valid
    @NotEmpty(message = "target发送的目标,不能为空")
    @ApiModelProperty(name = "target", value = "发送的目标；注意这里需要用数组，并且向数组内添加的用户不能超过1000个，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid", required = true, example = "[\"139715838410753\", \"139712941588481\"]")
    private List<
            @NotEmpty(message = "target发送的目标,目标ID不能为空")
                    String> target;

    @NotNull(message = "msg消息内容，对象不能为空")
    @ApiModelProperty(name = "msg", value = "msg消息内容，对象", required = true)
    private Msg msg;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Msg {

        @ApiModelProperty(name = "type", value = "消息类型；txt:文本消息，img：图片消息，loc：位置消息，audio：语音消息，video：视频消息，file：文件消息", example = "txt",hidden = true)
        private String type = "txt";

        @NotNull(message = "type消息类型，不能为Null")
        @ApiModelProperty(name = "msg", value = "消息内容", required = true, example = "txt")
        private String msg;
    }

    @NotNull(message = "from,消息发送者的会员ID，不能为空")
    @Min(value = 1, message = "from,消息发送者的会员ID，不正确")
    @ApiModelProperty(name = "from", value = "消息发送者的会员ID", required = true, example = "139712941588481")
    private Long from;

    public String getReqBodyJson() {
        return new Gson().toJson(this);
    }
}
