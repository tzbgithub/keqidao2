package com.qidao.application.controller.official;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.qidao.application.model.common.Result;
import com.qidao.application.service.official.WxchatOfficalService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/wechat")
@Slf4j
@Api(value = "App组织机构", tags = "App组织机构")
public class WxchatOfficalController {
    @Autowired
    private WxchatOfficalService wxchatOfficalService;

    @PostMapping(value = "/getWxChatTokent")
    public ResponseResult<String> getWxChatTokent() {
        String wxChatTokent = wxchatOfficalService.getAccessToken();
        return Result.ok(wxChatTokent);
    }

    @PostMapping(value = "/qrCodeTicket")
    public ResponseResult qrCodeTicket() {
        Object obj = wxchatOfficalService.qrCodeTicket();
        return Result.ok(obj);
    }

    @PostMapping(value = "/generatorQrCodeByTicket")
    public ResponseResult generatorQrCodeByTicket() {
        Object obj = wxchatOfficalService.generatorQrCodeByTicket("");
        return Result.ok(obj);
    }

    /**
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/official/event")
    Object event(HttpServletRequest request) {
        log.info("official -> event -> start -> params -> {}", request.getParameterMap());

        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        boolean check = wxchatOfficalService.signCheck(timestamp, nonce, signature);
        log.info("official -> event -> check -> {}",check);

        String body = ServletUtil.getBody(request);
        log.info("event -> body -> {} isNotBlank -> {}", body,StrUtil.isNotBlank(body));

        if (check) {
            if(StrUtil.isNotBlank(body)) {
                Object obj = wxchatOfficalService.eventExecute(body);
                log.info("execute -> done -> res -> {}", obj);
            }
             /*
            若确认此次GET请求来自微信服务器，请原样返回 echostr 参数内容
             */
            String echostr = request.getParameter("echostr");
            return echostr;
        }
        return Result.ok();
    }
    @GetMapping(value = "/official/checkContent")
    public boolean checkContent(String content) {
        boolean flag = wxchatOfficalService.checkContent(content);
        return  flag;
    }
}

