package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @date : 2021/3/19 3:56 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetGroupMembersRes", description = "[响应]分页获取群组成员")
public class EasemobGetGroupMembersRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "memberList", value = "群组成员列表", example = "[{\"member\": \"user1\"},{\"member\": \"user2\"}]")
    private List<EasemobGetGroupMembersRes.Member> memberList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Member{

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "member", value = "群组成员的会员ID",example = "xqs4hdspto2og7qk")
        private String member;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "owner", value = "群组创建者的会员ID",example = "glrynbc9ovcsgose")
        private String owner;
    }
}
