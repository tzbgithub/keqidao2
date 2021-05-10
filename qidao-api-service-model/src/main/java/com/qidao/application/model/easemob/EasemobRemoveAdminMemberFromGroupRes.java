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
 * @date : 2021/3/22 9:20 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveAdminMemberFromGroupRes", description = "[响应]移除群管理员")
public class EasemobRemoveAdminMemberFromGroupRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "移除结果，success表示成功", example = "success")
    private String result;

    @ApiModelProperty(name = "oldadmin", value = "被移除管理员身份的会员ID", example = "139712941588481")
    private String oldadmin;
}
