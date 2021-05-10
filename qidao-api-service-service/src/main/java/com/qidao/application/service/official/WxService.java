package com.qidao.application.service.official;

public interface WxService {

    String getUnionId(String code);

    /**
     * 创建二维码ticket
     * @return
     */
    Object qrCodeTicket();
}
