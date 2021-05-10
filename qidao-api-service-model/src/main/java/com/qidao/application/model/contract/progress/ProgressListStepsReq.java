package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/7 3:40 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProgressListStepsReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 当前会员的用户id
     */
    @NotNull
    private Long memberId;

    /**
     * 合同id
     */
    @NotNull
    private Long contractId;
}
