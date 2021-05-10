package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @date : 2021/3/19 5:06 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupBatchRes", description = "[响应]批量移除群组成员")
public class EasemobRemoveMemberFromGroupBatchRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "respList", value = "每个用户的删除结果")
    private List<EasemobRemoveMemberFromGroupBatchRes.Resp> respList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resp{
        @ApiModelProperty(name = "result", value = "删除结果，true表示删除成功，false表示删除失败", example = "true")
        private Boolean result;

        @ApiModelProperty(name = "action", value = "执行的操作，remove_member,移除群组成员", example = "remove_member")
        private String action;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "reason", value = "删除失败的原因", example = "user: user1 doesn't exist in group: 66213271109633")
        private String reason;

        @ApiModelProperty(name = "groupid", value = "操作的群组ID", example = "143021205159938")
        private String groupid;

        @ApiModelProperty(name = "user", value = "被移除成员的会员ID", example = "139715838410753")
        private String user;
    }
}
