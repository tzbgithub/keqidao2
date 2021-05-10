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
 * @date : 2021/3/15 10:41 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveSuperAdminFromChatRoomRes", description = "[响应]移除超级管理员")
public class EasemobRemoveSuperAdminFromChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "newSuperAdmin", value = "被移除的超级管理员的用户名", example = "xNpTf4VRXbMnd91u")
    private String newSuperAdmin;
}
