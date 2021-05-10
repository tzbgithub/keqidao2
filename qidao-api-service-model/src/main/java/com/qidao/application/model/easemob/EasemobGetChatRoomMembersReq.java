package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/10 1:32 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetChatRoomMembersReq", description = "[请求]分页获取聊天室成员")
public class EasemobGetChatRoomMembersReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @NotNull(message = "pageNum页码,不能为空")
    @Min(value = 1,message = "pageNum页码，最小值为1")
    @ApiModelProperty(name = "pageNum", value = "页码", required = true, example = "1")
    private Integer pageNum = 1;

    @NotNull(message = "pageSize页面大小,不能为空")
    @Min(value = 1,message = "pageSize页面大小，最小值为1")
    @ApiModelProperty(name = "pageSize", value = "页面大小", required = true, example = "1")
    private Integer pageSize = 1;
}
