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
 * @date : 2021/3/19 4:30 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupBatchRes", description = "[响应]批量添加群组成员")
public class EasemobAddMemberToGroupBatchRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "newmembers", value = "添加的会员ID集合",example = "[\"139715762913281\", \"139715813244929\"]")
    private List<Long> newmembers;

    @ApiModelProperty(name = "action", value = "执行的操作，add_member表示向聊天室添加成员", example = "add_member")
    private String action;

    @ApiModelProperty(name = "groupid", value = "群组ID", example = "66213271109633")
    private String groupid;

}
