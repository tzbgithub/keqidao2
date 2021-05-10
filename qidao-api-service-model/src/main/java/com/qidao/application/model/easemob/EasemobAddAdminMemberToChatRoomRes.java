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
 * @date : 2021/3/11 1:21 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddAdminMemberToChatRoomRes", description = "[响应]添加聊天室管理员")
public class EasemobAddAdminMemberToChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "操作结果；success：添加成功",example = "success")
    private String result;

    @ApiModelProperty(name = "newadmin", value = "添加为聊天室管理员的用户ID", example = "139712941588481")
    private Long newadmin;
}
