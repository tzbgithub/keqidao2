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
 * @date : 2021/3/15 1:32 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetGroupDetailByIdRes", description = "[响应]根据群组ID获取此群组的详情")
public class EasemobGetGroupDetailByIdRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "groupDetailList", value = "群组详情信息列表")
    List<EasemobGetGroupDetailByIdRes.GroupDetail> groupDetailList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupDetail {

        @ApiModelProperty(name = "id", value = "聊天室 ID，聊天室唯一标识符，由环信服务器生成", example = "66211860774913")
        private String id;

        @ApiModelProperty(name = "name", value = "聊天室名称，任意字符串", example = "测试聊天室001")
        private String name;

        @ApiModelProperty(name = "description", value = "聊天室描述，任意字符串", example = "这是一个简单的测试聊天室")
        private String description;

        @ApiModelProperty(name = "membersonly", value = "加入聊天室是否需要群主或者群管理员审批。true：是，false：否", example = "true")
        private Boolean membersonly;

        @ApiModelProperty(name = "allowinvites", value = "是否允许聊天室成员邀请别人加入此聊天室。true：允许聊天室成员邀请人加入此聊天室，false：只有聊天室管理员才可以往聊天室加人", example = "true")
        private Boolean allowinvites;

        @ApiModelProperty(name = "invite_need_confirm", value = "邀请成员加入是否需要经过验证", example = "true")
        private Boolean invite_need_confirm;

        @ApiModelProperty(name = "maxusers", value = "聊天室成员上限，创建聊天室的时候设置,可修改", example = "200")
        private Integer maxusers;

        @ApiModelProperty(name = "created", value = "聊天室的创建时间(毫秒)", example = "1615193123425")
        private Long created;

        @ApiModelProperty(name = "affiliations_count", value = "现有聊天室成员总数", example = "2")
        private Integer affiliations_count;

        @ApiModelProperty(name = "affiliations", value = "现有成员列表，包含了owner和member")
        private List<EasemobGetGroupDetailByIdRes.Affiliation> affiliations;

        @ApiModelProperty(name = "public", value = "聊天室类型：true：公开聊天室，false：私有聊天室",example = "true")
        @JsonProperty("public")
        @SerializedName("public")
        private Boolean isPublic;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Affiliation {

        @ApiModelProperty(name = "member", value = "聊天室成员的 username", example = "user2")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String member;

        @ApiModelProperty(name = "owner", value = "聊天室创建者的 username", example = "user1")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String owner;
    }
}
