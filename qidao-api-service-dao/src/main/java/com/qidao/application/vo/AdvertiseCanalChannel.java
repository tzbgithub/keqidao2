package com.qidao.application.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据渠道号和频道号过滤广告信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class AdvertiseCanalChannel {
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
    @ApiModelProperty("广告类型  0-APP内打开H5 1-外部H5打开 2-打开APP页面")
    private Integer type;
    @ApiModelProperty("图片路径")
    private String img;
    @ApiModelProperty("渠道ID")
    private Long canalId;
    @ApiModelProperty("频道ID")
    private Long channelId;
}
