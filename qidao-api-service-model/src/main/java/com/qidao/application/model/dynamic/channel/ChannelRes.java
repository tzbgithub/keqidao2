package com.qidao.application.model.dynamic.channel;

import com.qidao.application.model.aadvertise.AdvertiseInfoRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: xinfeng
 * @create: 2021-01-29 13:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelRes {

    @ApiModelProperty(name = "主键ID", value = "频道id")
    private Long id;

    @ApiModelProperty(name = "title", value = "频道标题(频道名)")
    private String title;

    @ApiModelProperty(name = "sequence", value = "排序值(越大越前)")
    private Integer sequence;

//    @ApiModelProperty("广告集合")
    private List<AdvertiseInfoRes> adList;
}
