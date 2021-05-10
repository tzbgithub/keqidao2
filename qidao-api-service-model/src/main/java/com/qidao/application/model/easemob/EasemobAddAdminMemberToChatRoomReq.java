package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/11 1:20 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddAdminMemberToChatRoomReq", description = "[请求]添加聊天室管理员")
public class EasemobAddAdminMemberToChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @NotNull(message = "会员ID，不能为空")
    @Min(value = 1,message = "会员ID，不正确")
    @ApiModelProperty(name = "newadmin", value = "将成为聊天室管理员的会员ID", required = true, example = "139712941588481")
    private Long newadmin;

    public String getReqBodyJson(){
        return "{\"newadmin\": \""+newadmin+"\"}";
    }
}
