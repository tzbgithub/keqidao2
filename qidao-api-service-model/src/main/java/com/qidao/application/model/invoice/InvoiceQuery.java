package com.qidao.application.model.invoice;

import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分页查询对象 --> 查询用户开具的发票相关信息")
public class InvoiceQuery extends BasePageQuery {

    @NotNull
    @ApiModelProperty(name = "memberId", value = "发票申请人ID--主键", required = true, example = "134329862782977")
    private Long memberId;

}
