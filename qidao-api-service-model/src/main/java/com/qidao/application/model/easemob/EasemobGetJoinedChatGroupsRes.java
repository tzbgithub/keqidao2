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
 * @date : 2021/3/15 1:04 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetJoinedChatGroupsRes", description = "[响应]获取一个用户参与的所有群组")
public class EasemobGetJoinedChatGroupsRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "groupList", value = "群组信息列表" ,example = "[{\"groupid\":\"66016455491585\",\"groupname\":\"testgroup1\"}]")
    private List<Group>  groupList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Group{
        @ApiModelProperty(name = "groupid", value = "群组ID", example = "66016455491585")
        private String groupid;

        @ApiModelProperty(name = "groupname", value = "群组名称", example = "testgroup1")
        private String groupname;
    }
}
