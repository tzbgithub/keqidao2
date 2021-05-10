package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("筛选行业返回对象")
public class ScreenIndustryRes {

    private Long id;

    private String val;

    private List<ChildIndustryRes> child;

}
