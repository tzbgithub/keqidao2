package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @date : 2021/3/11 10:00 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromChatRoomBatchReq", description = "[请求]批量删除聊天室成员")
public class EasemobRemoveMemberFromChatRoomBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @Valid
    @NotEmpty(message = "删除的用户名称,不能为空")
    @ApiModelProperty(name = "usernames", value = "被删除的聊天室成员的会员ID集合", required = true, example = "[139715838410753, 139712941588481]")
    private List<
            @NotNull(message = "username,会员ID，不能为空")
            @Min(value = 1,message = "username,会员ID，不正确")
                    Long> usernames;

    public String getUsernamesStr(){
        StringBuilder sb = new StringBuilder();
        for(Long id:usernames){
            sb.append(id).append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
