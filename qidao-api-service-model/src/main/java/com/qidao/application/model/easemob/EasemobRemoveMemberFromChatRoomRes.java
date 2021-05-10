package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/11 9:46 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromChatRoomRes", description = "[响应]删除单个聊天室成员")
public class EasemobRemoveMemberFromChatRoomRes  implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "删除结果，true表示删除成功，false表示删除失败", example = "true")
    private Boolean result;

    @ApiModelProperty(name = "action", value = "执行的操作，remove_member表示从聊天室删除成员", example = "remove_member")
    private String action;

    @ApiModelProperty(name = "id", value = "聊天室ID，聊天室唯一标识符，由环信服务器生成", example = "66213271109633")
    private String id;

    @ApiModelProperty(name = "user", value = "删除聊天室的用户的用户名", example = "user1")
    private String user;
}
