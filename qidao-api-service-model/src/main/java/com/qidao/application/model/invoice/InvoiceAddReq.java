package com.qidao.application.model.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ApiModel(value = "新增发票对象")
public class InvoiceAddReq{

    @ApiModelProperty(name = "orderId", value = "订单ID", required = true, example = "44345435435")
    private Long orderId;

    @ApiModelProperty(name = "type", value = "发票类别：0-企业发票 1-个人发票", required = true, example = "1")
    @Range(min = 0, max = 1, message = "参数范围0~1：发票类别：0-企业发票 1-个人发票")
    private Integer type;

    @ApiModelProperty(name = "invoiceHead", value = "发票抬头", required = true, example = "企岛上海")
    private String invoiceHead;

    @Pattern(regexp = "/^[A-Z0-9]{15}$|^[A-Z0-9]{18}$|^[A-Z0-9]{20}$/")
    @ApiModelProperty(name = "dutyParagraph", value = "税号", required = true, example = "9144030071526726XG")
    private String dutyParagraph;

    @ApiModelProperty(name = "remark", value = "备注", example = "请速度开票")
    private String remark;

    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式不正确")
    @ApiModelProperty(name = "email", value = "email", required = true, example = "qidao@email.com")
    private String email;
}
