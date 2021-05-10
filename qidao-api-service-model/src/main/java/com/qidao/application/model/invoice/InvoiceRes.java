package com.qidao.application.model.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "InvoiceRes", description = "[响应]返回待查询的发票相关信息")
public class InvoiceRes {

    @ApiModelProperty(name = "organizationName", value = "组织名称")
    private String organizationName;

    @ApiModelProperty(name = "memberName", value = "开票人用户名称")
    private String memberName;

    @ApiModelProperty(name = "orderId", value = "订单ID")
    private Long orderId;

    @ApiModelProperty(name = "type", value = "发票类别：0-企业发票 1-个人发票")
    private Integer type;

    @ApiModelProperty(name = "invoiceHead", value = "发票抬头")
    private String invoiceHead;

    @ApiModelProperty(name = "createTime", value = "申请时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "dutyParagraph", value = "税号")
    private String dutyParagraph;

    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;

    @ApiModelProperty(name = "email", value = "电子邮箱")
    private String email;

    @ApiModelProperty(name = "status", value = "申请状态：0-申请中 1-已开票")
    private Integer status;
}
