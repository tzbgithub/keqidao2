package com.qidao.application.model.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServerInsesrtReq {

    @ApiModelProperty(name = "memberIdPartyA", value = "甲方（发布者）用户ID",required = true,example = "232423")
    @NotNull(message = "用户未指定")
    private Long memberIdPartyA;
    @ApiModelProperty(name = "title", value = "标题名称",required = true,example = "表头")
    @NotNull(message = "请输入标题")
    @Size(max = 100,message = "标题最长100长度")
    private String title;
    @ApiModelProperty(name = "status", value = "状态类别：0-草稿；1-已发布；2-已接受；3-已取消",required = true,example = "1")
    @NotNull(message = "发布状态未填写")
    private Integer status;
    @ApiModelProperty(name = "addressProvinceId", value = "地址省ID",required = false,example = "1")
    private Integer addressProvinceId;

    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称",required = false,example = "上海市")
    private String addressProvinceName;

    @ApiModelProperty(name = "addressCityId", value = "地址市id",required = false,example = "1")
    private Integer addressCityId;

    @ApiModelProperty(name = "addressCityName", value = "地址市名称",required = false,example = "上海市")
    private String addressCityName;

    @ApiModelProperty(name = "addressAreaId", value = "地址区ID",required = false,example = "1")
    private Integer addressAreaId;

    @ApiModelProperty(name = "addressAreaName", value = "地址区名称",required = false,example = "上海市")
    private String addressAreaName;
    @ApiModelProperty(name = "specAreaId", value = "需求领域id",required = false,example = "1")
    @NotNull(message = "请选择需求领域")
    private Long specAreaId;
    @NotNull(message = "请选择发布方式")
    @ApiModelProperty(name = "type", value = "0 全平台 1 发送给实验室老师",required = true,example = "1")
    private int type;
    @ApiModelProperty(name = "specAmountId", value = "研究经费id",required = true,example = "1")
    @NotNull(message = "请选择研究经费")
    private Long specAmountId;

    @Size(max = 400,message = "请控制在400字以内")
    @NotNull(message = "请输入需求描述")
    @ApiModelProperty(name = "description", value = "需求描述",example = "发布一个需求")
    private String description;
    @ApiModelProperty(name = "thumb", value = "视频缩略图(上传图片缩略图可不填写)",required = false,example = "xxx.jpg")
    private String thumb;
    @ApiModelProperty(name = "url", value = "图片或视频地址(图片可以多张视频只能是一张)",example = "['xxx.jpg']",required = false)
    private List<String> url;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "请输入期望解决时间")
    @ApiModelProperty(name = "solutionTime", value = "期望解决时间",required = true,example = "2020-02-02")
    private LocalDate solutionTime;
    @ApiModelProperty(name = "labelId", value = "标签标识",required = false,example = "[1,2,3]")
    private List<Long> labelId;
    @NotNull(message = "请指定传入图片还是视频")
    @ApiModelProperty(name = "imageType", value = "0 图片 1 视频",required = true,example = "1")
    private int imageType;
}
