package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Scanner;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("动态取消点赞请求参数对象")
public class DynamicDisagreeReq {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "666")
    private Long memberId;

    @NotNull(message = "动态ID不能为空")
    @ApiModelProperty(value = "动态ID" , required = true , example = "130894234976257")
    private Long dynamicId;

}
