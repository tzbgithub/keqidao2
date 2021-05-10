package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qidao.framework.service.ServicePage;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 *  {
 * <pre>
 *  sqlCount: 增删改 SQL语句返回的int值(影响的数据库记录数量)
 *  success : 增删改 是否成功(即sqlCount>0是否为真)
 *  codeMessageEnum : 增删改 失败时的提示信息(controller返回用的)
 *  req : controller传递的请求参数
 *  resList : 查询时使用的结果列表
 * </pre>
 * }
 * @date : 2020/12/14 3:58 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDTO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer sqlCount;

    @JsonIgnore
    private Boolean success;

    @JsonIgnore
    private SubscribeCodeMessageEnum codeMessageEnum;

    @JsonIgnore
    private ServicePage<List<SubscribeRes>> resList;

}
