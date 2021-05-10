package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("发布动态请求对象")
public class DynamicPushReq {

    @NotNull(message = "发布者ID不能为空")
    @ApiModelProperty(value = "发布者ID" , example = "134134370467842" , required = true)
    private Long memberId;

    @NotNull(message = "标题不能为空")
    @Size(max = 128 , message = "标题过长请适当精简")
    @ApiModelProperty(value = "标题" , example = "111111" , required = true)
    private String title;

    @ApiModelProperty(value = "链接" , example = "123" )
    private String url;

    @ApiModelProperty(value = "视频" , example = "1111" )
    private String video;

    @ApiModelProperty(value = "缩略图" , example = "123")
    private String thumb;

    @ApiModelProperty(value = "缩略内容" , example = "123" )
    private String summary;

    @ApiModelProperty(value = "图片" , example = "['123.jpg','456.jpg']" )
    private List<String> img;

    @ApiModelProperty(value = "内容" , example = "123")
    private String content;

    @NotNull(message = "标签不能为空")
    @ApiModelProperty(value = "标签ID数组",required = true)
    private Long[] labels;

    @ApiModelProperty(value = "技术成熟度" ,  example = "141352631336961")
    private Long technologyMaturity;

    @ApiModelProperty(value = "合作类型" , example = "141353170305025")
    private Long cooperationType;

    @NotNull(message = "发布类型不能为空")
    @ApiModelProperty(value = "发布类型" , required = true,example = "[1,2,3]")
    private Long[] articles;

    @ApiModelProperty(value = "展示类型：1-纯文字类型 2-图片类型 3-视频类型  4-分享类型 5-其他类型" , example = "1")
    private Integer type;

}
