package com.qidao.application.model.member.feedback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: xinfeng
 * @create: 2021-01-29 09:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "会员反馈提交对象")
public class FeedbackAddReq {

    @ApiModelProperty(name = "memberId", value = "用户ID--主键", example = "136149804843010", required = true)
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @ApiModelProperty(name = "reasonId", value = "原因ID:<br>" +
            "1 - 平台BUG<br>" +
            "2 - 内容违规<br>" +
            "3 - 内容侵权<br>" +
            "4 - 平台建议<br>" +
            "5 - 其他反馈<br>", example = "1", required = true)
    @Range(min = 1, max = 5, message = "参数范围不正确，必须是1~5")
    @NotNull(message = "原因ID不能为空")
    private Integer reasonId;

    @ApiModelProperty(name = "description", value = "反馈描述", example = "平台问题", required = true)
    @NotNull(message = "描述信息不能为空")
    @Size(max = 400,message = "内容最多400字")
    private String description;
}
