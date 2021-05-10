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
 * @create: 2021-01-29 14:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "频道更新对象 --> 根据主键更新频道标题和排序值")
public class ChannelUpdateReq {
    @NotNull(message = "频道ID不能为空")
    @ApiModelProperty(name = "id", value = "频道ID--主键", example = "1", required = true)
    private Long id;
    @NotNull(message = "频道标题不能为空")
    @ApiModelProperty(name = "title", value = "频道标题", example = "CCTV", required = true)
    private String title;
    @ApiModelProperty(name = "sequence", value = "排序值", example = "2")
    private int sequence;
}
