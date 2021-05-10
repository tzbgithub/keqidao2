package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/19 5:43 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddAdminMemberToGroupReq", description = "[请求]添加群管理员")
public class EasemobAddAdminMemberToGroupReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @NotNull(message = "newadmin,会员ID，不能为空")
    @Min(value = 1,message = "newadmin,会员ID，不正确")
    @ApiModelProperty(name = "newadmin", value = "会员ID", required = true, example = "139712941588481")
    private Long newadmin;

    public String getReqBodyJson(){
        return "{\"newadmin\": \""+newadmin+"\"}";
    }

}
