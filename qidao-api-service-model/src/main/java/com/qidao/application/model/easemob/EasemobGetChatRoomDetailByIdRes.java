package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @date : 2021/3/8 4:58 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetChatRoomDetailByIdRes", description = "[响应]获取聊天室详情")
public class EasemobGetChatRoomDetailByIdRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "聊天室 ID，聊天室唯一标识符，由环信服务器生成", example = "142391087529986")
    private String id;

    @ApiModelProperty(name = "name", value = "聊天室名称，任意字符串", example = "聊天室_002")
    private String name;

    @ApiModelProperty(name = "description", value = "聊天室描述，任意字符串",example = "该聊天室是一个测试房间")
    private String description;

    @ApiModelProperty(name = "membersonly", value = "加入聊天室是否需要群主或者群管理员审批。true：是，false：否",example = "false")
    private Boolean membersonly;

    @ApiModelProperty(name = "allowinvites", value = "是否允许聊天室成员邀请别人加入此聊天室。 true：允许聊天室成员邀请人加入此聊天室，false：只有聊天室管理员才可以往聊天室加人",example = "false")
    private Boolean allowinvites;

    @ApiModelProperty(name = "maxusers", value = "聊天室成员上限，创建聊天室的时候设置，可修改",example = "200")
    private Integer maxusers;

    @ApiModelProperty(name = "owner", value = "聊天室创建者的username",example = "2xfbggkl8dwwdwen")
    private String owner;

    @ApiModelProperty(name = "created", value = "聊天室的创建时间(毫秒)",example = "1615193123425")
    private Long created;

    @JsonProperty(value = "public")
    @SerializedName("public")
    @ApiModelProperty(name = "public", value = "聊天室类型：true：公开聊天室，false：私有聊天室",example = "true")
    public Boolean ifPublic;

    @ApiModelProperty(name = "affiliations_count", value = "现有聊天室成员总数",example = "2")
    public Integer affiliations_count;

    @ApiModelProperty(name = "affiliations", value = "现有成员列表，包含了 owner 和 member。例如： “affiliations”:[{“owner”: “user1”},{“member”:”user2”},{“member”:”user3”}]",example = "[{\"member\": \"gbnq69bjtkj4h87n\"},{\"owner\": \"2xfbggkl8dwwdwen\"}]")
    public List<EasemobGetChatRoomDetailByIdRes.Affiliation> affiliations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Affiliation {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "member", value = "聊天室成员的username",example = "gbnq69bjtkj4h87n")
        private String member;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "owner", value = "聊天室创建者的username",example = "2xfbggkl8dwwdwen")
        private String owner;
    }
}
