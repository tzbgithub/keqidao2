package com.qidao.application.model.search;

import com.qidao.application.model.dynamic.DynamicLabelRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("搜索服务返回参数")
public class SearchServerPageRes {

    @ApiModelProperty(value = "服务ID")
    private Long serverId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "缩略图")
    private String thumb;

    @ApiModelProperty(value = "链接")
    private String url;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "研发经费")
    private String money;

    @ApiModelProperty(value = "标签")
    private List<DynamicLabelRes> labels;

}
