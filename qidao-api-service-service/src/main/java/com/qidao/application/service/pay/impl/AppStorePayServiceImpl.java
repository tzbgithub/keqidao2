package com.qidao.application.service.pay.impl;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.pay.*;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.model.pay.enums.PayWayEnum;
import com.qidao.application.service.order.OrderService;
import com.qidao.application.service.pay.PayService;
import com.qidao.application.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AppStorePayServiceImpl implements PayService {
    @Value("${pay.apple.url}")
    private String url;
    @Value("${pay.apple.password}")
    private String password;
    private String sandboxUrl = "https://sandbox.itunes.apple.com/verifyReceipt";
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Override
    public Integer getPayWay() {
        return PayWayEnum.APPLE.getType();
    }

    @Override
    public PayRes invoke(PolicyPayDTO req) {
        return null;
    }

    @Override
    public void callback(PayCallbackReq req) throws Exception {
        log.info("AppStorePayServiceImpl -> callback -> req -> {}", req);
        Map<String, Object> param = req.getResourceMap();
        param.put("password", password);

        log.info("AppStorePayServiceImpl -> callback -> http -> url -> {}", url);
        String respondStr = HttpRequest.post(url)
                .contentType(ContentType.JSON.getValue())
                .body(JSONUtil.toJsonStr(param))
                .execute().body();
        log.info("AppStorePayServiceImpl -> callback -> http -> respond -> {}", respondStr);
        ApplePayVerifyResponse response = new ApplePayVerifyResponse(respondStr);

        boolean isVerifyProd = response.isVerify();
        log.info("AppStorePayServiceImpl -> callback -> isVerifyProd -> {}", isVerifyProd);
        Map<String, String> checkParam = req.getCheckParam();
        Long orderNo = Long.parseLong(checkParam.get("orderNo"));
        if (!isVerifyProd) {
            // 如果是 21007 ,再查询一次沙盒环境
            Integer status = response.getStatus();
            boolean needSandBoxQuery = Integer.valueOf(21007).equals(status);
            log.info("AppStorePayServiceImpl -> callback -> response -> status -> {} needSandBoxQuery -> {}", status, needSandBoxQuery);
            if (needSandBoxQuery) {
                String sandboxRespondStr = HttpRequest.post(sandboxUrl)
                        .contentType(ContentType.JSON.getValue())
                        .body(JSONUtil.toJsonStr(param))
                        .execute().body();
                response = new ApplePayVerifyResponse(sandboxRespondStr);
                log.info("AppStorePayServiceImpl -> callback -> http -> 沙盒环境 -> respond -> {}", respondStr);
                boolean isSandboxResponse = response.isVerify();
                if (!isSandboxResponse) {
                    log.warn("AppStorePayServiceImpl -> callback -> 未支付密钥 -> 沙盒环境 -> {}", orderNo);
                    throw new BusinessException(PayExceptionEnum.APPLE_FAIL);
                }
            } else {
                log.warn("AppStorePayServiceImpl -> callback -> 未支付密钥  -> {}", orderNo);
                throw new BusinessException(PayExceptionEnum.APPLE_FAIL);
            }
        }
        CustomOrder order = orderService.selectByOrderNo(orderNo);

        // 验证订单惟一
        String thirdOrderNo = Optional.ofNullable(response.getReceipt())
                .map(ApplePayVerifyReceiptResponse::getTransactionId)
                .orElse(null);

        // 产品一致性
        String thirdProductId = Optional.ofNullable(response.getReceipt())
                .map(ApplePayVerifyReceiptResponse::getProductId)
                .orElse(null);

        Long productSkuId = order.getProductSkuId();
        Boolean isSameProduct = productService.isOneProduct(productSkuId, thirdProductId);
        log.info("AppStorePayServiceImpl -> callback -> orderProduct -> productSkuId -> {} thirdProductId -> {} isSameProduct -> {}", productSkuId, thirdProductId, isSameProduct);
        if (!isSameProduct) {
            log.warn("AppStorePayServiceImpl -> callback -> 产品不同  -> {}", orderNo);
            throw new BusinessException(PayExceptionEnum.APPLE_VERIFY);
        }
        Integer orderQuantity = order.getQuantity();
        Integer appleQuantity = Optional.ofNullable(response.getReceipt())
                .map(ApplePayVerifyReceiptResponse::getQuantity)
                .map(Integer::valueOf)
                .orElse(0);
        if (!orderQuantity.equals(appleQuantity)) {
            log.warn("AppStorePayServiceImpl -> callback -> 数量不同  -> {}", orderNo);
            throw new BusinessException(PayExceptionEnum.APPLE_VERIFY);
        }

        log.info("AppStorePayServiceImpl -> callback -> orderNo -> {}", orderNo);

        orderService.orderDone(orderNo, getPayWay(), false, thirdOrderNo,isVerifyProd);
    }
}
