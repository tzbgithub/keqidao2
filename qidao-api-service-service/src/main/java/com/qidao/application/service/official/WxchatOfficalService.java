package com.qidao.application.service.official;

public interface WxchatOfficalService {
    /**
     * 获取微信token
     * @return
     */
    public  String getAccessToken();

    /**
     * 根据unionId调用第三方查询用户信息
     */
    public  void   getUserInfoByUnionId(String accessToken,String openId);

    Object qrCodeTicket();

    Object generatorQrCodeByTicket(String ticket);

    Object officialQrScan(String xmlStr);

    Object eventExecute(String xmlStr);

    String getUnionId(String code);

    /**
     * <p>验证是否是微信发送的参数</p>
     * <p>将token、timestamp、nonce 三个参数进行字典序排序 </p>
     * <p>将三个参数字符串拼接成一个字符串进行sha1加密</p>
     * <p>开发者获得加密后的字符串可与 signature 对比</p>
     * @param nonce 随机字符串
     * @param signature 签名
     * @param timestamp 时间戳
     * @return true - 验证通过 false - 验证失败
     */
    boolean signCheck(String timestamp,String nonce,String signature);

    /**
     * 验证信息是否含有敏感词汇
     * @param content
     */
    boolean  checkContent(String content);
}
