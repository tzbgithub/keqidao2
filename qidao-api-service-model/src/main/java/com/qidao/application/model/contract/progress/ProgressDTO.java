package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 1:27 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDTO implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Integer sqlCount;

    @JsonIgnore
    private Boolean success;

    @JsonIgnore
    private ProgressCodeMessageEnum codeMessageEnum;

    @JsonIgnore
    private ProgressDetailRes detail;

    @JsonIgnore
    private List<ProgressStepRes> stepList;
}
