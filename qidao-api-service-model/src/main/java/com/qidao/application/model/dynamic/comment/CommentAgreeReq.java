package com.qidao.application.model.dynamic.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("点赞/取消点赞请求对象")
public class CommentAgreeReq {

    @NotNull(message = "评论ID不能为空")
    @ApiModelProperty(value = "评论ID" , required = true , example = "123")
    private Long commentId;

    @NotNull(message = "会员ID不能为空")
    @ApiModelProperty(value = "会员ID" , required = true , example = "123123")
    private Long memberId;

}
