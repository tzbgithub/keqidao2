package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 举例子：
 * <pre>
 * {
 *   "path": "/users",
 *   "uri": "https://a1.easemob.com/1112210205042338/keqidao/users",
 *   "timestamp": 1614581827765,
 *   "organization": "1112210205042338",
 *   "application": "f8859a44-179b-487d-a838-f2a22e84efc3",
 *   "entities": [
 *     {
 *       "uuid": "56901d70-7a5b-11eb-8d28-b9a82b5248b0",
 *       "type": "user",
 *       "created": 1614581827789,
 *       "modified": 1614581827789,
 *       "username": "user002",
 *       "activated": true
 *     }
 *   ],
 *   "action": "post",
 *   "duration": 57,
 *   "applicationName": "keqidao"
 * }
 * </pre>
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/1 3:17 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRegisterNoTokenRes", description = "[响应]注册用户(无需IM-token的版本)")
public class EasemobRegisterNoTokenRes implements Serializable {

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

    @ApiModelProperty(name = "entities", value = "实体")
    private List<Entities> entities;

    @ApiModelProperty(name = "action", value = "请求方式", example = "post")
    private String action;

    @ApiModelProperty(name = "duration", value = "耗时", example = "57")
    private Integer duration;

    @ApiModelProperty(name = "applicationName", value = "应用名", example = "keqidao")
    private String applicationName;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Entities {

        @ApiModelProperty(name = "uuid", value = "uuid", example = "56901d70-7a5b-11eb-8d28-b9a82b5248b0")
        private String uuid;

        @ApiModelProperty(name = "type", value = "类别", example = "user")
        private String type;

        @ApiModelProperty(name = "created", value = "创建时间", example = "1614581827789")
        private Long created;

        @ApiModelProperty(name = "modified", value = "修改时间", example = "1614581827789")
        private Long modified;

        @ApiModelProperty(name = "username", value = "用户名", example = "user002")
        private String username;

        @ApiModelProperty(name = "activated", value = "是否活跃", example = "true")
        private Boolean activated;
    }

}
