package com.qidao.application.model.dynamic;

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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("动态详情响应对象")
public class DynamicDetailedRes {

    @ApiModelProperty(value = "发布人头像")
    private String headImage;

    @ApiModelProperty(value = "发布人ID")
    private Long PublisherId;

    @ApiModelProperty(value = "发布人单位")
    private String belong;

    @ApiModelProperty(value = "发布人实验室名称")
    private String organizationName;

    @ApiModelProperty(value = "发布人名称")
    private String name;

    @ApiModelProperty(value = "发布人职称")
    private String position;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "关注状态0-已关注,1-未关注")
    private Integer subscribeStatus;

    @ApiModelProperty(value = "收藏状态0-已收藏,1-未收藏")
    private Integer favorStatus;

    @ApiModelProperty(value = "点赞状态0-已点赞,1-未点赞")
    private Integer likeStatus;

    @ApiModelProperty(value = "动态标签")
    private List<DynamicLabelRes> labels;

    @ApiModelProperty(value = "动态ID")
    private Long dynamicId;

    @ApiModelProperty(value = "发布时间")
    private String pushTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "链接")
    private String url;

    @ApiModelProperty(value = "视频路径" )
    private String video;

    @ApiModelProperty(value = "评论数" )
    private Integer commentNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "封面图")
    private String thumb;

    @ApiModelProperty(value = "图片")
    private List<String> img;

    @ApiModelProperty(value = "阅读权限true-有，false-无")
    private Boolean permissions;

    @ApiModelProperty(value = "技术成熟度")
    private String technologyMaturity;

    @ApiModelProperty(value = "合作类型")
    private String cooperationType;


    @ApiModelProperty(value = "展示类型：1-纯文字类型 2-图片类型 3-视频类型  4-分享类型 5-其他类型")
    private Integer type;

    @ApiModelProperty(value = "动态发布类型")
    private List<DynamicSelectConfigRes> articles;

}
