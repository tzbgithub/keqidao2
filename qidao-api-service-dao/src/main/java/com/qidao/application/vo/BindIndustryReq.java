package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindIndustryReq {
    @NotNull(message = "用户不能为空")
    @ApiModelProperty(name = "memberId", value = "用户表示", required = true,example = "234234234234234")
    private Long memberId;

    @ApiModelProperty(name = "industry", value = "行业标识", required = true,example = "234234234234234")
    private Long industry;


    @ApiModelProperty(name = "industryIds", value = "行业标识", required = true,example = "[234234234234234,33312313]")
    @Size(max = 10 , message = "所选行业过多，请适当精简")
    private List<Long> industryIds;
}
