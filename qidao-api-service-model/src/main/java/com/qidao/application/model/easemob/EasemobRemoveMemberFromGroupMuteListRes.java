package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/23 2:28 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupMuteListRes", description = "[响应]从群组的禁言列表中移除用户")
public class EasemobRemoveMemberFromGroupMuteListRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "userList", value = "取消禁言的用户集合", example = "[{\"result\": true,\"user\": 139715762913281}]")
    List<EasemobRemoveMemberFromGroupMuteListRes.Members> userList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Members {

        @ApiModelProperty(name = "result", value = "取消禁言是否成功", example = "true")
        private String result;

        @ApiModelProperty(name = "user", value = "取消禁言的会员ID", example = "139715762913281")
        private Long user;
    }
}
