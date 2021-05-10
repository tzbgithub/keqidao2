package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户行业")
public class MemberIndustryRes {

    private Long id;

    private String val;

}
