package com.qidao.application.model.dynamic.channel;

import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * @author: xinfeng
 * @create: 2021-01-29 14:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "频道分页查询对象 --> 根据标题查询频道")
public class ChannelQuery extends BasePageQuery {
    @ApiModelProperty(name = "title", value = "频道标题", example = "CCTV", required = true)
    @NotNull(message = "频道标题不能为空")
    private String title;
}


