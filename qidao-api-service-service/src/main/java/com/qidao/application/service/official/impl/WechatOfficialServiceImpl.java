package com.qidao.application.service.official.impl;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qidao.application.model.common.Token;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.service.official.WxchatOfficalService;
import com.qidao.application.vo.offical.WxChatUnionRep;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WechatOfficialServiceImpl implements WxchatOfficalService {
    @Autowired
    private RedissonClient redissonClient;

    // todo apollo 配置 (Autuan)[3.31]
    private String token = "WlnUft0M1VU5Nte5ZMeceUuOWbjwqzdu";
    private String appid = "wx0a442882af9eac05";
    private String secret = "eb32e3bb0110faec4ffc1299a5a42420";

    @Override
    public String getAccessToken() {
        String token = null;
        RBucket<String> bucket = redissonClient.getBucket("wxchat::get::token::");
        log.info("WechatOfficialServiceImpl -> getAccessToken -> isExists -> {}", bucket.isExists());
        if (bucket.isExists()) {
            log.info("WechatOfficialServiceImpl -> getAccessToken -> redis 命中 -> 响应");
            token = bucket.get();
            return token;
        }
        token = Token.getAccessToken(appid, secret);
        log.info("WechatOfficialServiceImpl -> bucket -> token -> {}", token);
        bucket.set(token, 2, TimeUnit.HOURS);
        return token;
    }

    /**
     * 根据unionId调用第三方查询用户信息
     */
    @Override
    public void getUserInfoByUnionId(String accessToken, String openId) {
        WxChatUnionRep wxChatUnionRep = new WxChatUnionRep();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info" + "?access_token=" + accessToken + "&openid=" + openId;
        String jsonResult = HttpUtil.get(url);
        JSONObject json = JSONObject.parseObject(jsonResult);
        String subscribe = json.getString("subscribe");
        String openid = json.getString("openid");
        String nickname = json.getString("nickname");
        String sex = json.getString("sex");
        String city = json.getString("city");
        String country = json.getString("country");
        String province = json.getString("province");
        String headimgurl = json.getString("headimgurl");
        String unionid = json.getString("unionid");
        JSONArray tagidList = json.getJSONArray("tagid_list");
        List<Integer> tagidLists = JSONObject.parseArray(tagidList.toJSONString(), Integer.class);
        wxChatUnionRep.setCity(city).setSubscribe(Integer.valueOf(subscribe)).setCountry(country).setSex(Integer.valueOf(sex)).
                setHeadimgurl(headimgurl).setNickname(nickname).setUnionid(unionid).setOpenid(openid).setTagidList(tagidLists).setProvince(province);
    }

    @Override
    public Object qrCodeTicket() {
        /*
        临时二维码请求说明

        http请求方式: POST URL: https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
        POST数据格式：json
            POST数据例子：
                {"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
            或者也可以使用以下POST数据创建字符串形式的二维码参数：
            {"expire_seconds": 604800, "action_name": "QR_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
         */
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("expire_seconds","604800");
        param.put("action_name","QR_STR_SCENE");

        Map<String, Object> scene = new HashMap<>();
        Map<String, Object> sceneStr = new HashMap<>();
        sceneStr.put("scene_str", "test");
        scene.put("scene",sceneStr);
        param.put("action_info",scene);

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + getAccessToken();
        log.info("qrCodeTicket -> url -> {}",url);

        String paramStr = JSONUtil.toJsonStr(param);
        log.info("qrCodeTicket -> paramStr -> {}",paramStr);
        String post = HttpUtil.post(url, paramStr);

    /*

    "data": "{\"ticket\":\"gQE68DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybkMxazFub0JjTEYxb0RmMU53Y2QAAgSnVDhgAwSAOgkA\",\"expire_seconds\":604800,\"url\":\"http:\/\/weixin.qq.com\/q\/02nC1k1noBcLF1oDf1Nwcd\"}",

     */
        log.info("post -> {}",post);
        return post;
    }

    @Override
    public Object generatorQrCodeByTicket(String ticket) {
        /*
        通过ticket换取二维码

        获取二维码ticket后，开发者可用ticket换取二维码图片。请注意，本接口无须登录态即可调用。

        请求说明

        HTTP GET请求（请使用https协议）https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET 提醒：TICKET记得进行UrlEncode

        返回说明

        ticket正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。

        HTTP头（示例）如下： Accept-Ranges:bytes Cache-control:max-age=604800 Connection:keep-alive Content-Length:28026 Content-Type:image/jpg Date:Wed, 16 Oct 2013 06:37:10 GMT Expires:Wed, 23 Oct 2013 14:37:10 +0800 Server:nginx/1.4.1

        错误情况下（如ticket非法）返回HTTP错误码404。
         */
        ticket = "gQE68DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybkMxazFub0JjTEYxb0RmMU53Y2QAAgSnVDhgAwSAOgkA";

        ticket = URLUtil.encode(ticket);
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        log.info("generatorQrCodeByTicket -> url -> {}",url);
        String respond = HttpUtil.get(url);
        log.info("generatorQrCodeByTicket -> respond -> {}",respond);
        return respond;
    }

    @Override
    public Object officialQrScan(String xmlStr) {
        /*
            如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
            如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
         */

        /*

        用户未关注时，进行关注后的事件推送

        <xml>
          <ToUserName><![CDATA[toUser]]></ToUserName>
          <FromUserName><![CDATA[FromUser]]></FromUserName>
          <CreateTime>123456789</CreateTime>
          <MsgType><![CDATA[event]]></MsgType>
          <Event><![CDATA[subscribe]]></Event>
          <EventKey><![CDATA[qrscene_123123]]></EventKey>
          <Ticket><![CDATA[TICKET]]></Ticket>
        </xml>
         */


        /*
        用户已关注时的事件推送

        <xml>
          <ToUserName><![CDATA[toUser]]></ToUserName>
          <FromUserName><![CDATA[FromUser]]></FromUserName>
          <CreateTime>123456789</CreateTime>
          <MsgType><![CDATA[event]]></MsgType>
          <Event><![CDATA[SCAN]]></Event>
          <EventKey><![CDATA[SCENE_VALUE]]></EventKey>
          <Ticket><![CDATA[TICKET]]></Ticket>
        </xml>
         */

        /*
            ToUserName	开发者微信号
            FromUserName	发送方帐号（一个OpenID）
            CreateTime	消息创建时间 （整型）
            MsgType	消息类型，event
            Event	事件类型，SCAN
            EventKey	事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
            Ticket	二维码的ticket，可用来换取二维码图片
         */
        Map<String, Object> paramMap = XmlUtil.xmlToMap(xmlStr);

        log.info("officialQrScan -> paramMap -> {}",paramMap);

        Object toUserName = paramMap.get("ToUserName");
        // SCAN 模式下 ， msgId (eventKey ) 为 salesmanId
        String eventKey = String.valueOf(paramMap.get("EventKey"));
        // 根据 FromUserName 查询对应的 unionId
        String fromUserName = String.valueOf(paramMap.get("FromUserName"));
        String unionId = getUnionId(fromUserName);
        RBucket<String> bucket = redissonClient.getBucket("wechat::newMember::" + unionId);
        bucket.set(unionId);
        // todo 测试通过后验证
        bucket.expire(30, TimeUnit.MINUTES);

        log.info("officialQrScan -> paramMap -> end");
        return "";
    }

    @Override
    public Object eventExecute(String xmlStr) {
        log.info("eventExecute -> xmlStr -> {}",xmlStr);
        String token = "WlnUft0M1VU5Nte5ZMeceUuOWbjwqzdu";
        String aesKey = "kBOX9LIA1dVYDQFwdkD6Mvou3gRgmGQsTbGcO6MeKbx";

        /*
        安全模式下，第三方平台方收到以下带密文消息体：

        encrypt_msg =
        <xml>
        <ToUserName></ToUserName>
        <Encrypt></Encrypt>
        </xml>
         */

        Map<String, Object> paramMap = XmlUtil.xmlToMap(xmlStr);
        log.info("officialQrScan -> paramMap -> {}",paramMap);
        String encrypt = String.valueOf(paramMap.get("Encrypt"));

        // 报文解密
        if(! StrUtil.isBlankOrUndefined(encrypt)) {
            AES aes = new AES(aesKey.getBytes(StandardCharsets.UTF_8));
            byte[] decrypt = aes.decrypt(Base64.getDecoder().decode(encrypt));
            String decryptStr = new String(decrypt);
            log.info("解密字符串 -> {}",decryptStr);
            paramMap = XmlUtil.xmlToMap(decryptStr);
        }

        Object toUserName = paramMap.get("ToUserName");
        String event = String.valueOf(paramMap.get("Event"));
        switch (event) {
            case "SCAN" : {
                log.info("officialQrScan -> paramMap -> event -> SCAN");
                break;
            }
            default:{
                log.warn("接收到未定义推送事件 ! -> event -> {}",event);
            }
        }

        return paramMap;
    }


    @Override
    public String getUnionId(String code) {
        log.info("WxServiceImpl -> getUnionId -> start -> code:{}" , code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        url = String.format(url, appid , secret , code);
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
    public boolean signCheck(String timestamp, String nonce, String signature) {
        log.info("signCheck -> start -> nonce -> {} timestamp -> {} ",nonce,timestamp);
        // todo apollo 配置 (Autuan)[2.27]
        String token = "WlnUft0M1VU5Nte5ZMeceUuOWbjwqzdu";

        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);

        String sign = SecureUtil.sha1(String.join("", list));
        boolean equals = sign.equals(signature);
        log.info("signCheck -> sign -> {} signature -> {} equal -> {}",sign,signature,equals);
        return equals;
    }

    /**
     * 验证信息是否含有敏感词汇
     * @param content
     */
    @Override
    public boolean checkContent(String content) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+accessToken + "&content="+content;
        String  jsonResult = HttpUtil.get(url);
        JSONObject json = JSONObject.parseObject(jsonResult);
        if(json!=null){
           String errcode = json.getString("errcode");
           String errmsg = json.getString("errmsg");
            if("0".equals(errcode)){
                if("ok".equals(errmsg)){
                    return true;
                }else if ("risky content".equals(errmsg)) {
                    throw new BusinessException(errmsg);
                }
            }else {
                throw new BusinessException(errmsg);
            }
        }
        return false;
    }

}
