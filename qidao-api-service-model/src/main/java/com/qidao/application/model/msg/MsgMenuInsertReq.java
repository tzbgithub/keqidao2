package com.qidao.application.model.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("新增消息菜单请求对象")
public class MsgMenuInsertReq {

    @ApiModelProperty(value = "父ID" , example = "0")
    private Long pid;

    @NotNull(message = "名字不能为空")
    @ApiModelProperty(value = "名字", required = true , example = "111")
    private String name;

    @ApiModelProperty(value = "封面图" ,example = "123123.jpg")
    private String thumb;

}
