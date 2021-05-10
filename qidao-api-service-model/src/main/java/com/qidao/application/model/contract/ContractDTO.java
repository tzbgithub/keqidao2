package com.qidao.application.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qidao.framework.service.ServicePage;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 2:37 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer sqlCount;

    @JsonIgnore
    private Boolean success;

    @JsonIgnore
    private ContractCodeMessageEnum codeMessageEnum;

    @JsonIgnore
    private ServicePage<List<ContractListItemRes>> contractList;
}
