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
 * @date : 2021/3/22 11:13 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupBlockListRes", description = "[响应]从群组黑名单移除单个用户")
public class EasemobRemoveMemberFromGroupBlockListRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "移除结果，true表示移除成功，false表示移除失败", example = "true")
    private Boolean result;

    @ApiModelProperty(name = "action", value = "执行的操作，remove_blocks表示把用户从黑名单中移除", example = "remove_blocks")
    private String action;

    @ApiModelProperty(name = "groupid", value = "群组ID", example = "143021205159938")
    private String groupid;

    @ApiModelProperty(name = "user", value = "从黑名单中移除的用户ID", example = "139715838410753")
    private String user;

}
