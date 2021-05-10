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
 * @date : 2021/3/22 10:39 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupBlockListBatchRes", description = "[响应]批量添加用户至群组黑名单")
public class EasemobAddMemberToGroupBlockListBatchRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "respList", value = "每个用户的添加结果")
    private List<EasemobAddMemberToGroupBlockListBatchRes.Resp> respList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resp{
        @ApiModelProperty(name = "result", value = "添加结果，true表示添加成功，false表示添加失败", example = "true")
        private Boolean result;

        @ApiModelProperty(name = "action", value = "执行的操作，add_blocks表示添加用户到群组黑名单", example = "add_blocks")
        private String action;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ApiModelProperty(name = "reason", value = "添加失败的原因", example = "user: user1 doesn't exist in group: 66213271109633")
        private String reason;

        @ApiModelProperty(name = "groupid", value = "群组ID", example = "143021205159938")
        private String groupid;

        @ApiModelProperty(name = "user", value = "被添加至黑名单的会员ID", example = "139715821633537")
        private String user;
    }
}
