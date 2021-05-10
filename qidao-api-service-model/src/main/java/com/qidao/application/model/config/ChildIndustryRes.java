package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("行业子类")
public class ChildIndustryRes {

    private Long id;

    private String val;

    private byte hot;

}
