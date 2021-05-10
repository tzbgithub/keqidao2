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
 * @date : 2021/3/11 5:05 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobCancelMemberMuteWithChatRoomReq", description = "[请求]移除禁言")
public class EasemobCancelMemberMuteWithChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @Valid
    @NotEmpty(message = "移除禁言的会员ID,不能为空")
    @ApiModelProperty(name = "usernames", value = "移除禁言的会员ID集合", required = true, example = "[139712941588481, 139715762913281]")
    private List<
            @NotNull(message = "username,会员ID，不能为空")
            @Min(value = 1, message = "username,会员ID，不正确")
                    Long> usernames;

    public String getUsernamesStr() {
        List<String> collect = usernames.stream().map(String::valueOf).collect(Collectors.toList());
        return String.join(",", collect);
    }
}
