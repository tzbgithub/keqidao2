package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "待添加的动态投诉对象")
public class ComplaintAddReq {

    @ApiModelProperty(name = "dynamicId", value = "投诉所属动态ID--主键", required = true, example = "133199541895169")
    private Long dynamicId;

    @NotNull(message = "投诉人不能为空")
    @ApiModelProperty(name = "memberId", value = "投诉者ID--主键", required = true, example = "134286401404930")
    private Long memberId;

    @NotNull(message = "投诉原因不能为空")
    @ApiModelProperty(name = "reasonId", value = "投诉原因ID--主键", required = true, example = "1339141476717920")
    private Long reasonId;

}
