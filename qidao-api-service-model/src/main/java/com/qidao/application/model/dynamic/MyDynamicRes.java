package com.qidao.application.model.dynamic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.member.assistant.AssistantBaseInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("我的动态返回对象")
public class MyDynamicRes {

    @ApiModelProperty(value = "动态ID")
    private Long dynamicId;

    @ApiModelProperty(value = "动态发布者ID")
    private Long publisherId;

    @ApiModelProperty(value = "动态发布时间")
    private String publishTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "链接")
    private String url;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "缩略图")
    private String thumb;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "审核状态 0-待审核 1-审核通过,未核准 2-审核拒绝 3-核准通过 4-核准拒绝")
    private Integer verifyStatus;

    @ApiModelProperty(value = "缩略内容")
    private String summary;

    @ApiModelProperty(value = "动态内容")
    private String dynamicContent;

    @ApiModelProperty(value = "发布者头像")
    private String headImage;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "图片")
    private List<String> img;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "动态发布人名字")
    private String name;

    @ApiModelProperty(value = "动态发布人单位")
    private String belong;

    @ApiModelProperty(value = "动态发布人实验室名字")
    private String organizationName;

    @ApiModelProperty(value = "职称")
    private String position;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "点赞状态0-已点赞，1-未点赞")
    private Integer likeStatus;

    @ApiModelProperty(value = "收藏状态0-已收藏，1-未收藏")
    private Integer favorStatus;

    @ApiModelProperty(value = "动态标签")
    private List<DynamicLabelRes> labels;

    @ApiModelProperty(value = "合作类型")
    private String cooperationType;

    @ApiModelProperty(value = "展示类型：1-纯文字类型 2-图片类型 3-视频类型  4-分享类型 5-其他类型")
    private Integer type;

    @ApiModelProperty(value = "点赞者头像")
    List<String> agreeHeadImages;

    @ApiModelProperty(value = "助手信息 为空不是助手发布")
    private AssistantBaseInfoDTO assistant;
}
