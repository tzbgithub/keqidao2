package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/11 4:40 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobSetMemberMuteWithChatRoomReq", description = "[请求]添加禁言")
public class EasemobSetMemberMuteWithChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "被禁言用户的会员ID", required = true, example = "139715821633537")
    private Long usernames;

    @NotNull(message = "muteDuration禁言的截止时间,不能为空")
    @Min(value = -1,message = "muteDuration禁言时间最小值为-1，表示永久禁言")
    @ApiModelProperty(name = "muteDuration", value = "禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为固定时间戳4638873600000，即2117-01-01 00:00:00）", required = true, example = "-1")
    private Long muteDuration;

    public String getReqBodyJson(){
        return "{\"usernames\": [\""+usernames+"\"], " +"\"mute_duration\": " +muteDuration+"}";
    }
}
