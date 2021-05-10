package com.qidao.application.model.aadvertise;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广告基本信息 响应封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseInfoRes {
    @ApiModelProperty("广告id")
    private Long id;
    @ApiModelProperty("广告标题")
    private String title;
    @ApiModelProperty("广告位置ID")
    private Long location;
    @ApiModelProperty("跳转路径")
    private String linkUrl;
    @ApiModelProperty("排序值")
    private String sequence;
    @ApiModelProperty("图片路径")
    private String img;
    @ApiModelProperty("频道ID")
    private Long channelId;
    @ApiModelProperty("广告类型  0-APP内打开H5 1-外部H5打开 2-打开APP页面")
    private Integer type;

}
