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

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/23 1:49 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupMuteListReq", description = "[请求]添加用户至群组的禁言列表")
public class EasemobAddMemberToGroupMuteListReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "被禁言用户的会员ID", required = true, example = "139715821633537")
    private Long username;

    @NotNull(message = "muteDuration禁言的截止时间,不能为空")
    @Min(value = -1,message = "muteDuration禁言时间最小值为-1，表示永久禁言")
    @ApiModelProperty(name = "muteDuration", value = "禁言的时间，单位毫秒，如果是“-1”代表永久（实际的到期时间为固定时间戳4638873600000，即2117-01-01 00:00:00）", required = true, example = "-1")
    private Long muteDuration;

    public String getReqBodyJson(){
        return "{\"usernames\": [\""+username+"\"], " +"\"mute_duration\": " +muteDuration+"}";
    }

}
