package com.qidao.application.model.pay;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ApplePayVerifyResponse {
    /**
     * environment
     * string
     * 生成收据的环境。
     * 可能的值：Sandbox, Production
     */
    private String environment;

    /**
     * is-retryable
     * boolean
     * 请求过程中发生错误的指示器。值为1表示临时问题：稍后会重新验证此收据。值为0表示无法解决的问题：不要重试此收据的验证。仅适用于状态代码21100-21199。
     */
    private Boolean isRetryable;
    /**
     * latest_receipt
     * byte
     * 最新的 Base64 编码应用收据。仅返回包含自动可再生订阅的收据。
     */
    private Byte latestReceipt;
    /**
     * ！WARN! 未解析此数据
     * latest_receipt_info
     * [responseBody.Latest_receipt_info]
     * 包含所有应用内购买交易的阵列。这不包括被应用标记为已完成的消耗品的交易。仅返回包含自动可再生订阅的收据。
     */
    private Object latestReceiptInfo;
    /**
     * ！WARN! 未解析此数据
     * pending_renewal_info
     * [responseBody.Pending_renewal_info]
     * 在 JSON 文件中，每个元素都包含产品标识的每个自动可再生订阅的待续订信息的阵列_id。仅返回包含自动可再生订阅的应用收据。
     */
    private Object pendingRenewalInfo;
    /**
     * ！WARN! 未解析此数据
     * receipt
     * responseBody.Receipt
     * 发送供验证的收据的 JSON 表示。
     */
    private ApplePayVerifyReceiptResponse receipt;
    /**
     * status
     * status
     * 收据有效时为0，如果存在错误，则为状态代码。状态代码反映了整个应用收据的状态。查看可能的状态代码和描述的状态。
     */
    private Integer status;

    public ApplePayVerifyResponse(String jsonStr){
        super();
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if(jsonObject.isEmpty()) {
            return;
        }
        this.status = jsonObject.getInt("status");
        if(jsonObject.size() < 2) {
            return;
        }
        this.environment = jsonObject.getStr("environment");
        this.isRetryable = jsonObject.getBool("is-retryable");
        this.latestReceipt = jsonObject.getByte("latest_receipt");
        this.receipt = new ApplePayVerifyReceiptResponse(jsonObject.getJSONObject("receipt"));
    }

    public boolean isVerify(){
        return Integer.valueOf(0).equals(status);
    }
}
