package com.qidao.application.model.search;

import com.qidao.application.model.dynamic.DynamicLabelRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("搜索动态结果返回对象")
public class SearchDynamicPageRes {

    @ApiModelProperty(value = "动态ID")
    private Long dynamicId;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "发布者ID")
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

    @ApiModelProperty(value = "缩略内容")
    private String summary;

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

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "合作类型")
    private String cooperation;

    @ApiModelProperty(value = "动态发布人实验室名字")
    private String organizationName;

    @ApiModelProperty(value = "职称")
    private String position;

    @ApiModelProperty(value = "点赞状态0-已点赞，1-未点赞")
    private Integer likeStatus;

    @ApiModelProperty(value = "收藏状态0-已收藏，1-未收藏")
    private Integer favorStatus;

    @ApiModelProperty(value = "动态标签")
    private List<DynamicLabelRes> labels;

    @ApiModelProperty(value = "点赞者头像")
    private List<String> agreeHeadImages;

    @ApiModelProperty(value = "展示类型：1-纯文字类型 2-图片类型 3-视频类型  4-分享类型 5-其他类型")
    private Integer type;
}
