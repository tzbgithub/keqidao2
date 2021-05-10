package com.qidao.application.model.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("修改消息菜单请求对象")
public class MsgMenuUpdateReq {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID" , required = true , example = "123")
    private Long id;

    @ApiModelProperty(value = "父ID" , example = "123")
    private Long pid;

    @NotNull(message = "名字不能为空")
    @ApiModelProperty(value = "名字" , required = true , example = "123")
    private String name;

    @ApiModelProperty(value = "封面图" , example = "123.jpg")
    private String thumb;

    @ApiModelProperty(value = "0-正常使用 1-关闭" , example = "0")
    @Max(value = 1,message = "状态不合法")
    @Min(value = 0,message = "状态不合法")
    private Integer status;

}
