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
 * @date : 2021/3/23 10:37 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupBlockListBatchRes", description = "[响应]移除群管理员")
public class EasemobRemoveMemberFromGroupBlockListBatchRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "respList", value = "每个用户的移除结果")
    private List<EasemobRemoveMemberFromGroupBlockListBatchRes.Resp> respList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resp{
        @ApiModelProperty(name = "result", value = "移除结果，true表示移除成功，false表示移除失败", example = "true")
        private Boolean result;

        @ApiModelProperty(name = "action", value = "执行的操作，remove_blocks表示把用户从群组黑名单中移除", example = "remove_blocks")
        private String action;

        @ApiModelProperty(name = "groupid", value = "群组ID", example = "143021205159938")
        private String groupid;

        @ApiModelProperty(name = "user", value = "从黑名单中移除的会员ID", example = "139715821633537")
        private String user;
    }
}
