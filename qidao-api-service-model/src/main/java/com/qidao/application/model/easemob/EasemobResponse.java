package com.qidao.application.model.easemob;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRegisterNoTokenRes", description = "环信基本响应封装")
public class EasemobResponse implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "path", value = "请求的url", example = "/users")
    private String path;

    @ApiModelProperty(name = "uri", value = "响应uri", example = "https://a1.easemob.com/1112210205042338/keqidao/users")
    private String uri;

    @ApiModelProperty(name = "timestamp", value = "响应时间戳", example = "1614581827765")
    private Long timestamp;

    @ApiModelProperty(name = "organization", value = "组织标识", example = "1112210205042338")
    private String organization;

    @ApiModelProperty(name = "application", value = "应用标识", example = "f8859a44-179b-487d-a838-f2a22e84efc3")
    private String application;

    @ApiModelProperty(name = "action", value = "请求方式", example = "post")
    private String action;

    @ApiModelProperty(name = "duration", value = "耗时", example = "57")
    private Integer duration;

    @ApiModelProperty(name = "applicationName", value = "应用名", example = "keqidao")
    private String applicationName;

    /**
     * 解析后 = List<JSONObject>
     */
    private List<Object> entities;

    private List<Object> data;

    /**
     * 获取请求响应数据
     * @return
     */
    public <T> List<T> getResponseData(Class<T> clazz){
        if (null == clazz) {
            return null;
        }
        List<Object> list =  CollUtil.isNotEmpty(entities) ? entities : data;
        List<T> result = new ArrayList<>();
        for(Object bean : list) {
            if(clazz.isInstance(bean)) {
                result.add((T) bean);
                continue;
            }
            if(bean instanceof JSONObject) {
//                bean = (JSONObject)bean;
                T t = ((JSONObject) bean).toJavaObject(clazz);
                result.add(t);
//                continue;
            }
        }
        return result;
    }

//    public EasemobResponse<T> parse(String text, Class<T> clazz){
////        EasemobResponse easemobResponse = JSONObject.parseObject(text, EasemobResponse.class);
//        JSONObject respondObj = JSONObject.parseObject(text);
//        List<T> entities = respondObj.getJSONArray("entities").toJavaList(clazz);
//        List<T> data = respondObj.getJSONArray("entities").toJavaList(clazz);
//        EasemobResponse response = respondObj.toJavaObject(this.getClass());
//        response.setEntities(entities);
//        response.setData(data);
//        return response;
//    }

}
