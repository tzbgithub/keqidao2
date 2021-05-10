package com.qidao.application.model.dynamic.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("发布评论请求参数")
public class CommentPushReq {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "123")
    private Long memberId;

    @NotNull(message = "动态ID不能为空")
    @ApiModelProperty(value = "动态ID" , required = true , example = "123")
    private Long dynamicId;

    @Size(max = 100 , message = "字数不能超过100字")
    @ApiModelProperty(value = "内容" , required = true , example = "123")
    private String content;

}
