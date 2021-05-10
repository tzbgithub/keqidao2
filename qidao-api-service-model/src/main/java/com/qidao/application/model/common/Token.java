package com.qidao.application.model.common;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public  class  Token {
    /**获取access_token*/
    private static final String ACCESS_TOKEN  = "https://api.weixin.qq.com/cgi-bin/token";
    private String accessToken;

    private Long expiryTime; //过期时间
    private static Token token = null;

    private Token() {
    }

    public static Token getInstance() {
        if(token == null){
            token = new Token();
        }
        return token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 从腾讯获取获取accessToken
     *  String accessToken = demoJson.getString("access_token");
     *  String expiresIn = demoJson.getString("expires_in");
     * @param appId
     * @param appSecret
     * @return
     */
    public static String getAccessToken(String appId, String appSecret){
        String result = null;
        String url = ACCESS_TOKEN + "?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        String  jsonResult = HttpUtil.get(url);
        JSONObject json = JSONObject.parseObject(jsonResult);
        result = json.getString("access_token");
        return result;
    }
}
