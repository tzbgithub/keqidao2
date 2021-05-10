package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/11 5:05 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobCancelMemberMuteWithChatRoomRes", description = "[响应]移除禁言")
public class EasemobCancelMemberMuteWithChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "userList", value = "取消禁言的用户集合", example = "[{\"result\": true,\"user\": 139715762913281}]")
    List<EasemobCancelMemberMuteWithChatRoomRes.Members> userList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Members {

        @ApiModelProperty(name = "result", value = "取消禁言是否成功", example = "true")
        private String result;

        @ApiModelProperty(name = "user", value = "取消禁言的会员ID", example = "139715762913281")
        private Long user;
    }
}
