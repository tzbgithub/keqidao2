package com.qidao.application.model.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询消息菜单响应对象")
public class MsgMenuQueryRes {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "父ID")
    private Long pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "封面图")
    private String thumb;

    @ApiModelProperty(value = "状态0-正常使用 1-关闭")
    private Integer status;

}
