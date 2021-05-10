package com.qidao.application.model.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        try {
            // 内网不查询
            if (IpUtils.internalIp(ip)) {
                return "内网IP";
            }
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("ip", ip);
            String rspStr = HttpUtil.post(IP_URL, paramMap);
            if (StrUtil.isBlank(rspStr)) {
                log.error("AddressUtils -> 获取地理位置异常 -> {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            JSONObject data = obj.getObject("data", JSONObject.class);
            String region = data.getString("region");
            String city = data.getString("city");
            address = region + " " + city;
            return address;
        } catch (Exception e) {
            log.warn("getRealAddressByIP error -> {}", e.getMessage());
            return address;
        }
    }
}
