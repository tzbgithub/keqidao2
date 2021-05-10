package com.qidao.application.controller.pay;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.internal.util.AlipaySignature;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.wrapper.QidaoRequestWrapper;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.pay.*;
import com.qidao.application.model.pay.enums.PayWayEnum;
import com.qidao.application.service.pay.PayService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付  控制器
 */
@RestController
@Slf4j
@Api(tags = "支付接口")
@RequestMapping("/pay")
public class PayController {
    private final Map<Integer, PayService> payServiceMap;

    public PayController(List<PayService> payServiceList) {
        payServiceMap = new HashMap<>(payServiceList.size());
        for (PayService service : payServiceList) {
            payServiceMap.put(service.getPayWay(), service);
        }
    }

    /**
     * 获取第三方支付唤醒参数
     *
     * @return 用于唤醒第三方支付的必要参数
     */
    @PostMapping("/invoke")
    @QiDaoPermission
    @ApiOperation(value = "获取第三方支付唤醒参数", notes = "目前实现 <strong style='color:red;'>微信APP支付 TYPE-0</strong> 、 <strong style='color:red;'>支付宝app支付 TYPE-3</strong>、 <strong style='color:red;'>支付宝网页支付 TYPE-6</strong>、 <strong style='color:red;'>微信native支付 TYPE-7</strong> 方式")
    ResponseResult<PayRes> pay(@RequestBody @Validated PolicyPayDTO req) {
        log.info("PayController -> pay -> req -> {}", req);
        PayRes res = payServiceMap.get(req.getType()).invoke(req);
        log.info("PayController -> pay -> res -> {}", res);

        return Result.ok(res);
    }

    /**
     * 支付回调 : 支付宝
     *
     * @param request HttpRequest
     */
    @PostMapping(value = "/ali/callback")
    @ApiOperation(value = "支付回调：非APP调用接口", notes = "<strong>此接口非APP调用接口</strong>")
    Object alipayCallback(HttpServletRequest request) throws Exception {
        log.info("PayController -> alipayCallback -> 接收到支付宝回调");
        //获取支付宝POST过来反馈信息
        String body = ServletUtil.getBody(request);
        log.info("PayController -> alipayCallback -> getBody -> {}", body);
        body = URLUtil.decode(body);
        log.info("PayController -> alipayCallback -> decode -> {}", body);

        Map requestParams = new HashMap();
        String[] param = body.split("&");
        for (String keyvalue : param) {
            String[] pair = keyvalue.split("=");
            if (pair.length == 2) {
                String key = pair[0];
                String val = pair[1];
                boolean isArray = JSONUtil.isJsonArray(val);
                if (isArray) {
                    JSONArray array = JSONUtil.parseArray(val);
                    val = array.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }
                val = new String(val.getBytes("ISO-8859-1"), "utf-8");
                requestParams.put(key, val);
            }
        }


        PayCallbackReq req = new PayCallbackReq();
        req.setCheckParam(requestParams);

        Map<String, Object> resourceMap = new HashMap<>(1);
        resourceMap.put("orderNo", requestParams.get("out_trade_no"));
        req.setResourceMap(resourceMap);

        payServiceMap.get(PayWayEnum.ALIPAY.getType()).callback(req);

        // 处理成功后返回 success字符告知支付宝此通知已经受理成功
        return "success";
    }

    /**
     * 支付回调 : 微信 <br>
     * 加密不能保证通知请求来自微信。微信会对发送给商户的通知进行签名，并将签名值放在通知的HTTP头Wechatpay-Signature。<br>
     * 商户应当验证签名，以确认请求来自微信，而不是其他的第三方。<br>
     */
    @PostMapping("/wechat/app/callback")
    @ApiOperation(value = "支付回调：非APP调用接口", notes = "<strong>此接口非APP调用接口</strong>")
    Object wechatCallback(@RequestBody WechatPayNotifyParamReq req, HttpServletRequest request) throws Exception {
        log.info("pay -> wechatCallback -> start");
        QidaoRequestWrapper requestWrapper = null;
        requestWrapper = (QidaoRequestWrapper) request;
        String body = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());
        log.info("body -> {}", body);

        String wechatPayTimestamp = request.getHeader("Wechatpay-Timestamp");
        String wechatPayNonceStr = request.getHeader("Wechatpay-Nonce");
        String wechatPayServerSignature = request.getHeader("Wechatpay-Signature");
        String wechatPaySerial = request.getHeader("Wechatpay-Serial");

        Map<String, String> checkParam = new HashMap<>();
        checkParam.put("wechatPayTimestamp", wechatPayTimestamp);
        checkParam.put("wechatPayNonceStr", wechatPayNonceStr);
        checkParam.put("wechatPayServerSignature", wechatPayServerSignature);
        checkParam.put("wechatPaySerial", wechatPaySerial);

        checkParam.put("bodyStr", JSONUtil.toJsonStr(req));
        log.info("checkParam -> {}", checkParam);

        PayCallbackReq callbackReq = new PayCallbackReq();
        callbackReq.setCheckParam(checkParam);

        WechatPayNotifyParamItem resource = req.getResource();
        JSONObject jsonObject = JSONUtil.parseObj(resource);
        callbackReq.setResourceMap(jsonObject);

        log.info("pay -> wechatCallback -> callbackReq -> {}", callbackReq);
        payServiceMap.get(PayWayEnum.WECHAT_APP.getType()).callback(callbackReq);


        //        当商户后台应答失败时，微信支付将记录下应答的报文，建议商户按照以下格式返回。
        /*
                {
            "code": "SUCCESS",
            "message": "成功",
        }
         */
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.put("code", "SUCCESS");
        resultMap.put("message", "成功");
        return resultMap;
    }

    /**
     * 支付回调：苹果 <br>
     * 由客户端发送
     *
     * @return
     */
    @SneakyThrows
    @PostMapping("/ios/app/orderDone")
    @ResponseBody
    @ApiOperation(value = "苹果支付 支付成功调用")
    ResponseResult<Object> applePayCallback(@RequestBody ApplePayVerifyReq obj) {
        log.info("PayController -> applePayCallback -> start");
        PayCallbackReq payCallbackReq = obj.genPayCallbackReq();
        payServiceMap.get(PayWayEnum.APPLE.getType()).callback(payCallbackReq);

        log.info("PayController -> applePayCallback -> end");
        return Result.ok();
    }

    /**
     * 银联支付回调
     */
    void unionPayCallback(HttpServletRequest req) {


    }

}
