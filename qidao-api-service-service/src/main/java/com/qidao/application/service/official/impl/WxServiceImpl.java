package com.qidao.application.service.official.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.service.official.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service("wxService")
@Slf4j
public class WxServiceImpl implements WxService {
    /**
     * const appid = 'wx8e659b5c61db5d97' //appid
     * const secret = '2916d8d1068323c93bb45fc7667d255d' //secret
     */

    private static final String APPID = "wx8e659b5c61db5d97";
    private static final String SECRET = "eb32e3bb0110faec4ffc1299a5a42420";

    @Override
    public String getUnionId(String code) {
        log.info("WxServiceImpl -> getUnionId -> start -> code:{}" , code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        url = String.format(url, APPID , SECRET , code);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String body = responseEntity.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        String errCode = jsonObject.getString("errcode");
        String unionId = jsonObject.getString("unionid");
        log.info("WxServiceImpl -> getUnionId -> 判断errCode是否为-1 -> errCode：{}",errCode);
        if ("-1".equals(errCode)) {
            log.info("WxServiceImpl -> getUnionId -> 失败：系统繁忙，此时请开发者稍候再试");
            throw new BusinessException("系统繁忙，此时请开发者稍候再试");
        }
        log.info("WxServiceImpl -> getUnionId -> 判断errCode是否为40029 -> errCode：{}",errCode);
        if ("40029".equals(errCode)) {
            log.info("WxServiceImpl -> getUnionId -> 失败：code 无效");
            throw new BusinessException("code 无效");
        }
        log.info("WxServiceImpl -> getUnionId -> 判断errCode是否为45011 -> errCode：{}",errCode);
        if ("45011".equals(errCode)) {
            log.info("WxServiceImpl -> getUnionId -> 失败：频率限制，每个用户每分钟100次");
            throw new BusinessException("频率限制，每个用户每分钟100次");
        }
        log.info("WxServiceImpl -> getUnionId -> end");
        return unionId;
    }

    @Override
    public Object qrCodeTicket() {
        return null;
    }
}
