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
 * @date : 2021/3/10 1:56 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToChatRoomRes", description = "[响应]添加单个聊天室成员")
public class EasemobAddMemberToChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "添加结果，true表示添加成功，false表示添加失败", example = "true")
    private Boolean result;

    @ApiModelProperty(name = "action", value = "执行的操作，add_member表示向聊天室添加成员", example = "add_member")
    private String action;

    @ApiModelProperty(name = "id", value = "聊天室ID，聊天室唯一标识符，由环信服务器生成", example = "142391087529986")
    private String id;

    @ApiModelProperty(name = "user", value = "添加到聊天室的会员ID", example = "139715838410753")
    private Long user;
}
