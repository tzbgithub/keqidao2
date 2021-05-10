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
@ApiModel(value = "删除评论请求对象")
public class CommentDeleteReq {

    @NotNull(message = "评论ID不能为空")
    @ApiModelProperty(value = "评论ID" , required = true , example = "123")
    private Long commentId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "123")
    private Long memberId;

}
