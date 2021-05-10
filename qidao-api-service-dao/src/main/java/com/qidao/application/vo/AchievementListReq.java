package com.qidao.application.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AchievementListReq {
    @ApiModelProperty(name = "id", value = "主键")
    private Long id;
    @ApiModelProperty(name = "type", value = "类型")
    private Integer type;
    @ApiModelProperty(name = "video", value = "视频")
    private String video;
    @ApiModelProperty(name = "organizationId", value = "组织标识）")
    private Long organizationId;
    @ApiModelProperty(name = "title", value = "标题")
    private String title;
    @ApiModelProperty(name = "imgs", value = "图片")
    private String imgs;
    @ApiModelProperty(name = "summary", value = "摘要")
    private String summary;
    @ApiModelProperty(name = "content", value = "内容")
    private String content;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "maturity", value = "成熟度", required = false,example = "xxx.jpg")
    private Long maturity;
    @ApiModelProperty(name = "url", value = "链接", required = false,example = "xxx.jpg")
    private String url;
    @ApiModelProperty(name = "thumb", value = "缩略图", required = false,example = "xxx.jpg")
    private String thumb;
    @ApiModelProperty(name = "LabelName", value = "标签名称", required = false,example = "xxx.jpg")
    private List<LinkLabelVo> linkLabelVo;
    private String organizationName;
    private String name;
    private String belong;
    private String headImage;
    private String positionName;
    private String educationName;
    private Long positionId;
    private Long educationId;




}
