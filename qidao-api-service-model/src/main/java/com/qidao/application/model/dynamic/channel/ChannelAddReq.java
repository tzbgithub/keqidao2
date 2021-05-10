package com.qidao.application.model.dynamic.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: xinfeng
 * @create: 2021-01-29 13:54
 */
@Data
@Builder
@ApiModel(value = "频道新增对象 --> 新增频道记录")
@AllArgsConstructor
@NoArgsConstructor
public class ChannelAddReq {

    @ApiModelProperty(name = "title", value = "频道标题(频道名)", example = "第一频道", required = true)
    @NotNull(message = "频道标题不能为空")
    private String title;

    @ApiModelProperty(name = "sequence", value = "排序值(越大越前)", example = "1")
    private Integer sequence;
}
