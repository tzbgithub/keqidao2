package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
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
 * @date : 2021/3/10 1:32 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetChatRoomMembersRes", description = "[响应]分页获取聊天室成员")
public class EasemobGetChatRoomMembersRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "memberList", value = "聊天室成员列表", example = "[{\"member\": \"user1\"},{\"member\": \"user2\"}]")
    private List<EasemobGetChatRoomMembersRes.Member> memberList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Member{

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "member", value = "聊天室成员的username",example = "gbnq69bjtkj4h87n")
        private String member;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "owner", value = "聊天室创建者的username",example = "2xfbggkl8dwwdwen")
        private String owner;
    }

}
