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
 * @date : 2021/3/15 10:59 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetChatGroupsRes", description = "[响应]获取应用下全部的群组信息")
public class EasemobGetChatGroupsRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "groupList", value = "群组信息列表")
    List<EasemobGetChatGroupsRes.Group> groupList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Group{
        @ApiModelProperty(name = "owner", value = "群主的环信 ID。例如：{“owner”: “user1}", example = "easemob-demo#testapp_user1")
        private String owner;

        @ApiModelProperty(name = "groupid", value = "群组ID", example = "100743775617286960")
        private String groupid;

        @ApiModelProperty(name = "affiliations", value = "群组现有人数", example = "2")
        private Integer affiliations;

        @ApiModelProperty(name = "type", value = "“group”群组类型", example = "group")
        private String type;

        @ApiModelProperty(name = "lastModified", value = "最近一次修改的时间戳", example = "1441021038124")
        private String lastModified;

        @ApiModelProperty(name = "groupname", value = "群组名称", example = "testgroup2")
        private String groupname;
    }
}
