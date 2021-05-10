package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/19 4:50 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupRes", description = "[响应]移除单个群组成员")
public class EasemobRemoveMemberFromGroupRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "添加结果，true表示移除成功，false表示移除失败", example = "true")
    private boolean result;

    @ApiModelProperty(name = "groupid", value = "群组ID", example = "143021205159938")
    private String groupid;

    @ApiModelProperty(name = "action", value = "执行的操作", example = "remove_member")
    private String action;

    @ApiModelProperty(name = "user", value = "会员ID", example = "139715838410753")
    private String user;
}
