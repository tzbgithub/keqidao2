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
 * @date : 2021/3/8 5:52 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetJoinedChatRoomsByMemberNameRes", description = "[响应]获取用户加入的聊天室")
public class EasemobGetJoinedChatRoomsByMemberNameRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "chatRoomList", value = "用户加入的聊天室列表", example = "[{\"id\": \"66211860774913\",\"name\": \"testchatroom1\"},{\"id\": \"66211882795010\",\"name\": \"testchatroom2\"}]")
    private List<EasemobGetJoinedChatRoomsByMemberNameRes.ChatRoomProfile> chatRoomList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomProfile{

        @ApiModelProperty(name = "id", value = "聊天室ID，聊天室唯一标识符，由环信服务器生成", example = "142391087529986")
        private String id;

        @ApiModelProperty(name = "name", value = "聊天室名称，任意字符串", example = "聊天室_002")
        private String name;
    }
}
