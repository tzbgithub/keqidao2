package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
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
@ApiModel(value = "EasemobRemoveMemberFromChatRoomBatchRes", description = "[响应]批量删除聊天室成员")
public class EasemobRemoveMemberFromChatRoomBatchRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "respList", value = "每个用户的删除结果")
    private List<EasemobRemoveMemberFromChatRoomBatchRes.Resp> respList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resp{
        @ApiModelProperty(name = "result", value = "删除结果，true表示删除成功，false表示删除失败", example = "true")
        private Boolean result;

        @ApiModelProperty(name = "action", value = "执行的操作，remove_member表示从聊天室删除成员", example = "remove_member")
        private String action;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "reason", value = "删除失败的原因", example = "user: user1 doesn't exist in group: 66213271109633")
        private String reason;

        @ApiModelProperty(name = "id", value = "聊天室ID，聊天室唯一标识符，由环信服务器生成", example = "66213271109633")
        private String id;

        @ApiModelProperty(name = "user", value = "删除聊天室的用户的用户名", example = "user1")
        private String user;
    }
}
