package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "下拉对象")
public class SelectConfigVo {
    @ApiModelProperty(name = "id",value ="主键" ,required = true , example = "1")
    private Long id;

    @ApiModelProperty(name = "pid" , value = "父节点ID" , required = false )
    private Long pid;

    @ApiModelProperty(name = "val",value ="展示值" ,required = true , example = "test")
    private String val;

    @ApiModelProperty(value = "热门 0-否 1-是")
    private byte hot;

    @ApiModelProperty(name = "child" , value = "子节点" , required = false)
    private List<SelectSonVo> child;
}