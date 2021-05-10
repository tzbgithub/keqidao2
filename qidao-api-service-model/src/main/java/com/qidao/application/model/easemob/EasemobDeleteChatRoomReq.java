package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/10 11:26 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobDeleteChatRoomReq", description = "[请求]删除聊天室")
public class EasemobDeleteChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;
}
