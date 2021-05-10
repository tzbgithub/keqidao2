package com.qidao.application.model.order;

import cn.hutool.core.util.StrUtil;
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
@ApiModel
public class OrderAddressDTO {
    @ApiModelProperty(value = "地址省CODE",notes = "实物订单必传",example = "110000")
    private String addressProvinceCode;

    @ApiModelProperty(value = "地址省名称",notes = "实物订单必传",example = "安徽省")
    private String addressProvinceName;

    @ApiModelProperty(value = "地址市CODE",notes = "实物订单必传",example = "110000")
    private String addressCityCode;

    @ApiModelProperty(value = "地址市名称",notes = "实物订单必传",example = "上海市")
    private String addressCityName;

    @ApiModelProperty(value = "地址区CODE",notes = "实物订单必传",example = "110000")
    private String addressAreaCode;

    @ApiModelProperty(value = "地址区名称",notes = "实物订单必传",example = "松江区")
    private String addressAreaName;

    @ApiModelProperty(value = "详细地址",notes = "实物订单必传",example = "广富林路x号")
    private String addressDetail;

    @ApiModelProperty(value = "收货人姓名",notes = "实物订单必传",example = "张三")
    private String recipientName;

    @ApiModelProperty(value = "收货人联系电话",notes = "实物订单必传",example = "110000")
    private String recipientPhone;

    public boolean hasBlankVar(){
        return StrUtil.hasBlank(addressProvinceCode, addressProvinceName, addressCityCode, addressCityName, addressAreaCode, addressAreaName, addressDetail, recipientName, recipientPhone);
    }
}
