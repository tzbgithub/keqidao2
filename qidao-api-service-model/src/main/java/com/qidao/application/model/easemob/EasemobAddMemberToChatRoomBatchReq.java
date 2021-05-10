package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/10 2:24 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToChatRoomBatchReq", description = "[请求]批量添加聊天室成员")
public class EasemobAddMemberToChatRoomBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @Valid
    @NotEmpty(message = "添加的会员ID集合,不能为空")
    @ApiModelProperty(name = "usernames", value = "添加的会员ID", required = true, example = "[139715762913281, 139715813244929]")
    private List<
            @NotNull(message = "会员ID，不能为空")
            @Min(value = 1,message = "会员ID，不正确")
            Long> usernames;

    public String getReqBodyJson(){
        return "{\"usernames\": "+new Gson().toJson(usernames)+"}";
    }
}
