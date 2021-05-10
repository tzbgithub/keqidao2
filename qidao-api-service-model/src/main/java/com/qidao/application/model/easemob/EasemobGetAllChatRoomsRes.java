package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @date : 2021/3/10 10:21 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetAllChatRoomsRes", description = "[响应]获取APP中所有的聊天室")
public class EasemobGetAllChatRoomsRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "chatRoomList", value = "聊天室列表", example = "[{\"id\": \"66211860774913\",\"name\": \"testchatroom1\",\"owner\": \"user1\",\"affiliations_count\": 2},{\"id\": \"66211882795010\",\"name\": \"testchatroom2\",\"owner\": \"user2\",\"affiliations_count\": 2}]")
    private List<EasemobGetAllChatRoomsRes.ChatRoomProfile> chatRoomList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomProfile{

        @ApiModelProperty(name = "id", value = "聊天室 ID，聊天室唯一标识符，由环信服务器生成", example = "142391087529986")
        private String id;

        @ApiModelProperty(name = "name", value = "聊天室名称，任意字符串", example = "聊天室_002")
        private String name;

        @ApiModelProperty(name = "owner", value = "聊天室创建者的 username。例如：{“owner”: “user1”}", example = "139715762913281")
        private String owner;

        @SerializedName("affiliations_count")
        @ApiModelProperty(name = "name", value = "现有聊天室成员总数", example = "7")
        private Integer affiliationsCount;
    }
}
