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
 * @date : 2021/3/19 5:43 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddAdminMemberToGroupRes", description = "[响应]添加群管理员")
public class EasemobAddAdminMemberToGroupRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "newadmin", value = "添加为群组管理员的会员ID", example = "139712941588481")
    private String newadmin;
}
