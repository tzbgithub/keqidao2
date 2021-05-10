package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhousen
 * @date 2021/04/01 15:08
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDynamicCountVo {

    @ApiModelProperty(name = "countNum", value = "动态数量", example = "10")
    private Long countNum;

    @ApiModelProperty(name = "typeId", value = "类型id", example = "17930901")
    private Long typeId;

    @ApiModelProperty(name = "typeName", value = "类型名称", example = "共享仪器设备")
    private String typeName;
}
