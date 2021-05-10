package com.qidao.application.model.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ApplePayVerifyReq {
//    @ApiModelProperty(value = "Apple Pay 支付 十六进制密码",notes = "若此值不由订单变更，将会固定到服务器上",required = true)
//    private String password;

    @ApiModelProperty(value = "Apple Pay 支付 Base64 数据",required = true)
    private String receiptData;

    @ApiModelProperty(value = "订单号",example = "1234",required = true)
    private Long orderNo;
    ;

    public HashMap<String,Object> genResourceMap(){
        HashMap<String, Object> result = new HashMap<>();
//        result.put("password", password);
        result.put("receipt-data", receiptData);
        return result;
    }

    public PayCallbackReq genPayCallbackReq(){
        PayCallbackReq result = new PayCallbackReq();
        result.setResourceMap(genResourceMap());
        Map<String, String> checkParam = new HashMap<>();
        checkParam.put("orderNo", String.valueOf(orderNo));
        result.setCheckParam(checkParam);
        return result;
    }
}
