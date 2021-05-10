package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 *  {
 * <pre>
 *  其他Controller、Service调用SubscribeService接口时，需要传递该参数
 *  同时该参数也作为SubscribeService方法的返回值
 *  sqlCount: 增删改 SQL语句返回的int值(影响的数据库记录数量)
 *  success : 增删改 是否成功(即sqlCount>0是否为真)
 * </pre>
 * }
 * @date : 2020/12/17 10:06 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeEditDTO implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * sqlCount: 增删改 SQL语句返回的int值(影响的数据库记录数量)
     */
    @JsonIgnore
    private Integer sqlCount;

    /**
     * success : 增删改 是否成功(即sqlCount>0是否为真)
     */
    @JsonIgnore
    private Boolean success;
}