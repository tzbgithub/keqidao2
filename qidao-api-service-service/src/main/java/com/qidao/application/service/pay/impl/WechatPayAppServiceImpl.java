package com.qidao.application.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qidao.application.entity.order.CustomOrder;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.pay.*;
import com.qidao.application.model.pay.enums.PayConstantsEnum;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.model.pay.enums.PayWayEnum;
import com.qidao.application.model.pay.enums.WechatPayServerStatusEnum;
import com.qidao.application.service.order.OrderService;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service("WechatPayAppServiceImpl")
@Slf4j
public class WechatPayAppServiceImpl extends WeChatPayBaseServiceImpl {

    public WechatPayAppServiceImpl(@Autowired WechatPayConfig wechatPayConfig, @Autowired OrderService orderService) {
        super(wechatPayConfig, orderService);
    }


    @Override
    public Integer getPayWay() {
        return PayWayEnum.WECHAT_APP.getType();
    }

    @SneakyThrows
    @Override
    public PayRes invoke(PolicyPayDTO req) {
        log.info("WechatPayAppServiceImpl -> invoke -> start ");
        // 订单信息查询
        CustomOrder order = orderService.selectByOrderNo(req.getOrderNo());
        if (null == order) {
            log.info("WechatPayAppServiceImpl -> invoke -> null == order ");
            throw new BusinessException(PayExceptionEnum.ORDER_NULL);
        }
        if (order.getStatus() != 0) {
            log.info("WechatPayAppServiceImpl -> invoke -> order.getStatus() != 0 ");
            throw new BusinessException(PayExceptionEnum.ORDER_NULL);
        }

        String productName = StrUtil.blankToDefault(req.getProductName(), "会员服务");
        String orderDescription = "开通" + productName;

        // 订单价格
        BigDecimal payPrice = order.getPayPrice();
        Integer total = payPrice.multiply(new BigDecimal(100)).intValue();
        log.info("WechatPayAppServiceImpl -> invoke -> orderId -> {} payPrice -> {} total -> {} ", order.getId(), payPrice, total);

        WechatCreateOrderReq param = new WechatCreateOrderReq();

        WechatCreateOrderReq.AmountParam amount = param.new AmountParam();
        amount.setCurrency(PayConstantsEnum.CURRENCY.getStr());
        amount.setTotal(total);

        param.setAmount(amount);

        param.setAppid(appId);

        param.setDescription(orderDescription);
        param.setNotify_url(notifyUrl);
        param.setMchid(mchId);
        param.setOut_trade_no(String.valueOf(order.getNo()));
        log.info("WechatPayAppServiceImpl -> invoke -> orderId -> {} payPrice -> {} total -> {} ", order.getId(), payPrice, total);

        // 初始化httpClient
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();


        String reqData = JSONUtil.toJsonStr(param);
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");

        // 请求body参数
        StringEntity entity = new StringEntity(reqData, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        log.info("WechatPayAppServiceImpl -> invoke -> reqData -> {}", reqData);
        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        log.info("WechatPayAppServiceImpl -> invoke -> httpClientResponse -> {}", response);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("WechatPayAppServiceImpl -> invoke -> statusCode -> {}", statusCode);
            String responseStr = EntityUtils.toString(response.getEntity());

            if (statusCode == WechatPayServerStatusEnum.OK.getCode()) {
                JSONObject jsonObject = JSONUtil.parseObj(responseStr);
                log.info("WechatPayAppServiceImpl -> invoke -> responseStr -> {}", responseStr);

                String prepayId = jsonObject.getStr("prepay_id");
                String nonceStr = generatorNonceStr();
                long timestamp = System.currentTimeMillis() / 1000;
                String message = buildMessage(nonceStr, timestamp, prepayId);
                String sign = sign(message.getBytes(StandardCharsets.UTF_8));

                InvokeParam invokeParam = WeChatAppInvokeParam.builder()
                        .partnerid(mchId)
                        .prepayid(prepayId)
                        .noncestr(nonceStr)
                        .appid(appId)
                        .timestamp(String.valueOf(timestamp))
                        .sign(sign)
                        .build();

                return PayRes.builder()
                        .totalAmount(payPrice)
                        .orderNo(order.getNo())
                        .payWay(getPayWay())
                        .invokeParam(invokeParam)
                        .currency(PayConstantsEnum.CURRENCY.getStr())
                        .build();

            } else {
                log.error("WechatPayAppServiceImpl -> invoke -> 创建订单失败 -> orderNo -> {} -> 微信响应 -> {}", order.getId(), responseStr);
                throw new BusinessException("创建订单失败");
            }
        } finally {
            response.close();
        }
    }

}
