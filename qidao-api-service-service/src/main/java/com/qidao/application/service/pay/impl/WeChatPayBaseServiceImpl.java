package com.qidao.application.service.pay.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.pay.PayCallbackReq;
import com.qidao.application.model.pay.PayRes;
import com.qidao.application.model.pay.PolicyPayDTO;
import com.qidao.application.model.pay.WechatPayConfig;
import com.qidao.application.model.pay.enums.PayExceptionEnum;
import com.qidao.application.model.pay.util.WechatAesUtil;
import com.qidao.application.service.order.OrderService;
import com.qidao.application.service.pay.PayService;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 基础通用父类
 */
@Slf4j
public abstract class WeChatPayBaseServiceImpl implements PayService {
    protected final OrderService orderService;
    protected final String mchSerialNo;
    protected final AutoUpdateCertificatesVerifier verifier;
    protected final WechatAesUtil aesUtil;
    protected final String apiV3Key;
    protected final String notifyUrl;
    protected final String mchId;
    protected final String appId;
    protected PrivateKey merchantPrivateKey;

    public WeChatPayBaseServiceImpl(WechatPayConfig wechatPayConfig,
                                    OrderService orderService) {
        this.orderService = orderService;
        this.apiV3Key = wechatPayConfig.getApiV3Key();
        this.notifyUrl = wechatPayConfig.getNotifyUrl();
        this.mchId = wechatPayConfig.getMchId();
        this.appId = wechatPayConfig.getAppId();
        this.mchSerialNo = wechatPayConfig.getSerial();

        // 加载商户私钥（privateKey：私钥字符串）
        String privateKeyPath = wechatPayConfig.getPrivateKeyPath();
        PrivateKey merchantPrivateKey = null;
        boolean isFile = FileUtil.isFile(privateKeyPath);
        log.info("WechatPayAppServiceImpl -> privateKeyPath -> {} isFile -> {}", privateKeyPath, isFile);
        if (isFile) {
            String content = FileUtil.readString(privateKeyPath, StandardCharsets.UTF_8);
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            merchantPrivateKey = PemUtil
                    .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));

        } else {
            merchantPrivateKey = PemUtil
                    .loadPrivateKey(new ByteArrayInputStream(wechatPayConfig.getTestPrivateKey().getBytes(StandardCharsets.UTF_8)));
        }
        this.merchantPrivateKey = merchantPrivateKey;

        this.aesUtil = new WechatAesUtil(apiV3Key.getBytes(StandardCharsets.UTF_8));

        // 加载平台证书（mchId：商户号, mchSerialNo：商户证书序列号, apiV3Key：V3秘钥）
        this.verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public abstract Integer getPayWay();

    @Override
    public abstract PayRes invoke(PolicyPayDTO req);

    /**
     * 构建需要签名的字符串
     *
     * @param nonceStr  随机字符串
     * @param timestamp 时间戳
     * @param prepayId  预支付交易会话ID
     * @return 构建后的待签名字符串
     */
    protected String buildMessage(String nonceStr, long timestamp, String prepayId) {
        return appId + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + prepayId + "\n";
    }

    /**
     * 生成随机字符
     *
     * @return
     */
    protected String generatorNonceStr() {
        return IdUtil.simpleUUID();
    }

    /**
     * 微信支付签名
     *
     * @param message
     * @return
     */
    @SneakyThrows
    protected String sign(byte[] message) {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(merchantPrivateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }


    /**
     * 订单状态是否是已支付 <br>
     * 交易状态，枚举值：<br>
     * SUCCESS：支付成功 <br>
     * REFUND：转入退款 <br>
     * NOTPAY：未支付<br>
     * CLOSED：已关闭<br>
     * REVOKED：已撤销（付款码支付）<br>
     * USERPAYING：用户支付中（付款码支付）<br>
     * PAYERROR：支付失败(其他原因，如银行返回失败)<br>
     * 示例值：SUCCESS<br>
     *
     * @return
     */
    @SneakyThrows
    private boolean isPay(Long orderNo) {
        String queryUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + orderNo + "?mchid=" + mchId;
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("mchid", mchId);

        // 初始化httpClient
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));

        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();

        log.info("WechatPayAppServiceImpl -> invoke -> httpClient -> {}", JSONUtil.toJsonStr(httpClient));

        String reqData = JSONUtil.toJsonStr(queryMap);
        HttpGet http = new HttpGet(queryUrl);

        // 请求body参数
        StringEntity entity = new StringEntity(reqData);
        entity.setContentType("application/json");
        http.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(http);
        String responseStr = EntityUtils.toString(response.getEntity());
        log.info("responseStr -> {}", responseStr);
        JSONObject respondObj = JSONUtil.parseObj(responseStr);

        String tradeState = respondObj.getStr("trade_state");
        log.info("isPay -> {}", respondObj);
        return "SUCCESS".equals(tradeState);
    }

    @SneakyThrows
    @Override
    public void callback(PayCallbackReq req) {
        log.info("WechatPayAppServiceImpl -> callback -> start -> req -> {}", req);
        Map<String, String> checkParam = req.getCheckParam();

        String wechatPayServerSignature = checkParam.get("wechatPayServerSignature");
        log.info("WechatPayAppServiceImpl -> callback -> wechatPayServerSignature -> {}", wechatPayServerSignature);


        // 微信返回的都是 String : String 类型
        /*
            1、用商户平台上设置的APIv3密钥【微信商户平台—>账户设置—>API安全—>设置APIv3密钥】，记为key；
            2、针对resource.algorithm 中描述的算法（目前为 AEAD_AES_256_GCM），取得对应的参数nonce和associated_data；
            3、使用key、nonce和associated_data，对数据密文resource.ciphertext进行解密，得到JSON形式的资源对象；
         */
        Map<String, Object> resourceMap = req.getResourceMap();
        // 算法（目前为 AEAD_AES_256_GCM ）
        String algorithm = String.valueOf(resourceMap.get("algorithm"));

        boolean algorithmEqual = algorithm.equals("AEAD_AES_256_GCM");
        log.info("WechatPayAppServiceImpl -> callback -> algorithmEqual -> {} algorithm -> {}", algorithmEqual, algorithm);
        if (algorithmEqual) {
            String nonce = String.valueOf(resourceMap.get("nonce"));
            String associatedData = String.valueOf(resourceMap.get("associated_data"));
            String ciphertext = String.valueOf(resourceMap.get("ciphertext"));

            log.info("WechatPayAppServiceImpl -> callback -> aes -> nonce -> {} associatedData -> {} ciphertext -> {}", nonce, associatedData, ciphertext);
            String jsonResult = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
            log.info("WechatPayAppServiceImpl -> callback -> aes -> jsonResult -> {}", jsonResult);

            JSONObject resultObj = JSONUtil.parseObj(jsonResult);
            Long orderNo = resultObj.getLong("out_trade_no");

            if (!isPay(orderNo)) {
                log.warn("WechatPayAppServiceImpl -> callback -> isPay -> not pay ");
                throw new BusinessException(PayExceptionEnum.NOT_PAY);
            }

            // 订单完成
            log.info("WechatPayAppServiceImpl -> callback -> 订单完成 -> orderNo -> {}", orderNo);
            orderService.orderDone(orderNo, getPayWay());
        } else {
            log.error("微信：不支持的解密方式！ {}", algorithm);
            throw new BusinessException("微信：不支持的解密方式 ！ " + algorithm);
        }

        log.info("WechatPayAppServiceImpl -> callback -> end");
    }
}
