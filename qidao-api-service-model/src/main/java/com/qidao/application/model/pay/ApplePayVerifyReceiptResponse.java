package com.qidao.application.model.pay;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplePayVerifyReceiptResponse {
    /**
     * 机翻 : <br/>
     * 原始应用程序购买的时间<br/>
     * 太平洋时区<br/>
     * <br/>
     * original : <br/>
     * The time of the original app purchase, in the Pacific Time zone.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 2021-03-22 23:54:36 America/Los_Angeles
     */
    private String originalPurchaseDatePst;

    /**
     * 机翻 : <br/>
     * 对于消耗品、非消耗品和非续订订阅产品，App Store 以 UNIX 时代时间格式以毫秒为购买或恢复的产品向用户帐户收费的时间。<br/>
     * 对于自动续订订阅，App Store 在以 UNIX 时代时间格式以毫秒为失效后向用户帐户收取订阅购买或续订费用的时间。<br/>
     * 使用此时间格式处理日期。<br/>
     * <br/>
     * original : <br/>
     * For consumable, non-consumable, and non-renewing subscription products, the time the App Store charged the user’s account for a purchased or restored product, in the UNIX epoch time format, in milliseconds. <br/>
     * For auto-renewable subscriptions, the time the App Store charged the user’s account for a subscription purchase or renewal after a lapse, in the UNIX epoch time format, in milliseconds.<br/>
     * Use this time format for processing dates.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 1616482476200
     */
    private String purchaseDateMs;

    /**
     * 机翻 : <br/>
     * 原始购买的交易标识符<br/>
     * <br/>
     * original : <br/>
     * The transaction identifier of the original purchase.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 1000000792276844 <br/>
     * <br/>
     * <a href="https://developer.apple.com/documentation/appstorereceipts/original_transaction_id">访问详细信息</a>
     */
    private String originalTransactionId;

    /**
     * 机翻 : <br/>
     * 交易的独特标识符，如购买、恢复或续订<br/>
     * <br/>
     * original : <br/>
     * A unique identifier for a transaction such as a purchase, restore, or renewal.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 1000000792276844 <br/>
     * <br/>
     * <a href="https://developer.apple.com/documentation/appstorereceipts/transaction_id">访问详细信息</a>
     */
    private String transactionId;

    /**
     * 机翻 : <br/>
     * 购买的消费品数量<br/>
     * <br/>
     * original : <br/>
     * The number of consumable products purchased. <br/>
     * This value corresponds to the quantity property of the SKPayment object stored in the transaction’s payment property. <br/>
     * The value is usually “1” unless modified with a mutable payment.<br/>
     * The maximum value is 10.<br>
     * <br/>
     * 示例值  Example : <br/>
     * 1
     */
    private String quantity;

    /**
     * 机翻 : <br/>
     * 自动续订是否处于介绍性价格期的指标<br/>
     * <br/>
     * original : <br/>
     * An indicator of whether an auto-renewable subscription is in the introductory price period. <br/>
     * See is_in_intro_offer_period for more information.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * true
     */
    private String isInIntroOfferPeriod;

    /**
     * 机翻 : <br/>
     * 所购买产品的标识符<br/>
     * <br/>
     * original : <br/>
     * The unique identifier of the product purchased.<br/>
     * You provide this value when creating the product in App Store Connect, and it corresponds to the productIdentifier property of the SKPayment object stored in the transaction’s payment property.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 1234
     */
    private String productId;

    /**
     * 机翻 : <br/>
     * 应用商店以类似于 ISO 8601 的日期格式向用户帐户收取购买或恢复产品的费用，或者应用商店在失效后向用户帐户收取订阅购买或续订费用的时间。<br/>
     * <br/>
     * original : <br/>
     * The time the App Store charged the user’s account for a purchased or restored product, or the time the App Store charged the user’s account for a subscription purchase or renewal after a lapse, in a date-time format similar to ISO 8601.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 2021-03-23 06:54:36 Etc/GMT
     */
    private String purchaseDate;

    /**
     * 机翻 : <br/>
     * 订阅是否处于免费试用期的指标 <br/>
     * <br/>
     * original : <br/>
     * An indicator of whether a subscription is in the free trial period.  <br/>
     * <br/>
     * 示例值  Example : <br/>
     * false<br/>
     * <br/>
     * <a href="https://developer.apple.com/documentation/appstorereceipts/is_trial_period">访问详细信息</a>
     */
    private String isTrialPeriod;

    /**
     * 机翻 : <br/>
     * 应用商店为购买或恢复的产品向用户帐户收费的时间，或者 App Store 在太平洋时区失效后向用户帐户收取订阅购买或续订费用的时间。<br/>
     * <br/>
     * original : <br/>
     * The time the App Store charged the user’s account for a purchased or restored product, or the time the App Store charged the user’s account for a subscription purchase or renewal after a lapse, in the Pacific Time zone.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 2021-03-22 23:54:36 America/Los_Angeles
     */
    private String purchaseDatePst;

    /**
     * 机翻 : <br/>
     * 原始应用购买时间<br/>
     * <br/>
     * original : <br/>
     * The time of the original app purchase, in the Pacific Time zone.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 2021-03-22 23:54:36 America/Los_Angeles
     */
    private String originalPurchaseDate;

    /**
     * 机翻 : <br/>
     * 原始应用程序购买的时间，以 UNIX 时代时间格式，以毫秒为数毫秒。<br/>
     * 使用此时间格式处理日期。对于自动续订订阅，此值表示订阅的初始购买日期。<br/>
     * 原始购买日期适用于所有产品类型，并且在同一产品 ID 的所有交易中保持不变。<br/>
     * 此值对应于 StoreKit 中的原始交易日期属性。<br/>
     * <br/>
     * original : <br/>
     * The time of the original app purchase, in UNIX epoch time format, in milliseconds. <br/>
     * Use this time format for processing dates. For an auto-renewable subscription, this value indicates the date of the subscription’s initial purchase. <br/>
     * The original purchase date applies to all product types and remains the same in all transactions for the same product ID. <br/>
     * This value corresponds to the original transaction’s transactionDate property in StoreKit.<br/>
     * <br/>
     * 示例值  Example : <br/>
     * 1616482476200
     */
    private String originalPurchaseDateMs;

    /**
     * 下面是有实际响应但未说明的字段 <br/>
     * 文档未说明此字段  <a href="https://developer.apple.com/documentation/appstorereceipts/responsebody/latest_receipt_info">receipt 文档</a>
     */
    private String bid;
    private String bvrs;
    private String item_id;
    private String unique_identifier;
    private String unique_vendor_identifier;
    private String version_external_identifier;

    public ApplePayVerifyReceiptResponse(JSONObject jsonObject) {
        super();
        this.originalPurchaseDatePst = jsonObject.getStr("original_purchase_date_pst");
        this.purchaseDateMs = jsonObject.getStr("purchase_date_ms");
        this.originalTransactionId = jsonObject.getStr("original_transaction_id");
        this.transactionId = jsonObject.getStr("transaction_id");
        this.quantity = jsonObject.getStr("quantity");
        this.isInIntroOfferPeriod = jsonObject.getStr("is_in_intro_offer_period");
        this.productId = jsonObject.getStr("product_id");
        this.purchaseDate = jsonObject.getStr("purchase_date");
        this.isTrialPeriod = jsonObject.getStr("is_trial_period");
        this.purchaseDatePst = jsonObject.getStr("purchase_date_pst");
        this.originalPurchaseDate = jsonObject.getStr("original_purchase_date");
        this.originalPurchaseDateMs = jsonObject.getStr("original_purchase_date_ms");
    }
}
