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
 * @date : 2021/3/11 2:09 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveAdminMemberFromChatRoomRes", description = "[响应]移除聊天室管理员")
public class EasemobRemoveAdminMemberFromChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "操作结果；success：添加成功",example = "success")
    private String result;

    @ApiModelProperty(name = "oldadmin", value = "被移除聊天室管理员身份的用户ID", example = "81czeir8nljplo5t")
    private String oldadmin;
}
