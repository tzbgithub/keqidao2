package com.qidao.application.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 2:25 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContractListReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 当前会员的用户id
     */
    @NotNull
    private Long memberId;

    /**
     * 分页查询的页码(第n页),从1计数
     */
    @NotNull
    private Integer offset;

    /**
     * 一页的容量(页面共m条信息)
     */
    @NotNull
    private Integer limit;
}
