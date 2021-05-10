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

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/22 9:59 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupBlockListRes", description = "[响应]添加单个用户至群组黑名单")
public class EasemobAddMemberToGroupBlockListRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "添加结果，true表示添加成功，false表示添加失败", example = "true")
    private Boolean result;

    @ApiModelProperty(name = "action", value = "执行的操作，add_blocks表示添加用户至黑名单", example = "add_blocks")
    private String action;

    @ApiModelProperty(name = "groupid", value = "群组 ID", example = "143021205159938")
    private String groupid;

    @ApiModelProperty(name = "user", value = "被添加的用户 ID", example = "139712941588481")
    private String user;
}
