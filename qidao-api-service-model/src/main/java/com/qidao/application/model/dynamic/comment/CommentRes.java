package com.qidao.application.model.dynamic.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "评论对象")
public class CommentRes {

    @NotNull(message = "主键不能为空")
    @ApiModelProperty(name = "id" , value = "主键" , required = true , example ="111")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(name = "createTime" , value = "创建时间" , required = false)
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateTime" , value = "修改时间" , required = false)
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "createBy" , value = "创建者" , required = false )
    private Long createBy;

    @ApiModelProperty(name = "updateBy" , value = "修改者" , required = false )
    private Long updateBy;

    @ApiModelProperty(name = "delFlag" , value = "逻辑删除 0-否 1-是" , required = false, example ="0")
    private Byte delFlag;

    @NotNull(message = "评论者用户ID不能为空")
    @ApiModelProperty(name = "memberId" , value = "评论者用户ID" , required = true , example ="123")
    private Long memberId;

    @ApiModelProperty(name = "memberName" , value = "评论者名称" , required = false )
    private String memberName;

    @NotNull(message = "动态id不能为空")
    @ApiModelProperty(name = "dynamicId" , value = "动态id" , required = true , example ="333")
    private Long dynamicId;

    @ApiModelProperty(name = "memberHeadImg" , value = "评论者头像" , required = false )
    private String memberHeadImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(name = "commentTime" , value = "评论时间" , required = false )
    private LocalDateTime commentTime;

    @ApiModelProperty(name = "likeNum" , value = "点赞数" , required = false , example ="0")
    private Integer likeNum;

    @NotNull(message = "内容不能为空")
    @Size(max = 100,message = "最多评论100字")
    @ApiModelProperty(name = "content" , value = "内容" , required = true , example ="真好")
    private String content;

    @ApiModelProperty(value = "点赞状态 true-已点赞 false-未点赞")
    private boolean agreeStatus;

}
