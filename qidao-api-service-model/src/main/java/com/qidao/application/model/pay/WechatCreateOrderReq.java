package com.qidao.application.model.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WechatCreateOrderReq {
    /**
     * 非必填
     * 订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE
     * YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2018-06-08T10:34:56+08:00
     */
    private String time_expire;
    private AmountParam amount;

    private String mchid;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 通知URL必须为直接可访问的URL，不允许携带查询串。
     */
    private String notify_url;
    private String out_trade_no;
    /**
     * 非必填 <br>
     * 订单优惠标记 <br>
     * 示例值：WXG
     */
    private String goods_tag;
    private String appid;
    /**
     * 非必填： <br>
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;
    /**
     * 非必填 <br>
     * 优惠功能
     */
    private DetailParam detail;
    /**
     * 非必填 <br>
     * 支付场景信息
     */
    private SceneInfoParam scene_info;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AmountParam{
        /**
         * 非必填 <br>
         * CNY：人民币，境内商户号仅支持人民币
         */
        private String currency;
        /**
         * 订单总金额，单位为分
         */
        private Integer total;
    }

    @Data
    public class DetailParam{
        private String invoice_id;
        /**
         * 非必填 <br>
         * 单品列表信息 <br>
         * 条目个数限制：【1，6000】
         */
        private List<GoodDetailParam> goods_detail;
        private Integer cost_price;
    }

    @Data
    public class GoodDetailParam {
        private String goods_name;
        private String wechatpay_goods_id;
        private Integer quantity;
        private String merchant_goods_id;
        private Integer unit_price;
    }

    @Data
    public class SceneInfoParam {
        private String store_info;
        private String address;
        private String area_code;
        private String name;
        private String id;
    }
}
