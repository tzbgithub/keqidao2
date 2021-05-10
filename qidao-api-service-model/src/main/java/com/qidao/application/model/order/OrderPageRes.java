package com.qidao.application.model.order;

import com.qidao.framework.service.ServicePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OrderPageRes {
    @ApiModelProperty(value = "订单列表")
    private ServicePage<List<OrderVo>> list;

    @ApiModelProperty(value = "会员VIP结束时间（可能为空）",example = "2021-03-13 15:00:00")
    private String memberVipEndTime;
}
