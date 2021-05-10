package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/3 3:05 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EasemobUpdatePwdDTO implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Boolean updateSuccess;
}
