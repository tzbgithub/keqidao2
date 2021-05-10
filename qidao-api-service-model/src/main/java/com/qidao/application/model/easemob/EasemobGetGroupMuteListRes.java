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
 * @date : 2021/3/23 2:44 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetGroupMuteListRes", description = "[响应]获取群组的禁言列表")
public class EasemobGetGroupMuteListRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "mutedUserList", value = "被禁言的用户列表")
    List<EasemobGetGroupMuteListRes.MutedUser> mutedUserList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MutedUser{

        @ApiModelProperty(name = "expire", value = "禁言到期的时间戳（从1970年1月1日开始的毫秒数。如果禁言时间传的值为“-1”，那么该时间戳为固定的4638873600000，请参考mute_duration参数的说明）",example = "1489158589481")
        private Long expire;

        @ApiModelProperty(name = "user", value = "被禁言用户的 ID", example = "xNpTf4VRXbMnd91u")
        private String user;
    }
}
