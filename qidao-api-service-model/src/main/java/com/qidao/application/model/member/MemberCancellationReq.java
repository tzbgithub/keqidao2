package com.qidao.application.model.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 会员注销请求
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("会员注销请求")
public class MemberCancellationReq {
    @ApiModelProperty(name = "memberId", value = "用户标识", required = true,example = "123456")
    @NotNull(message = "用户id不能为空")
    private Long memberId;

    @ApiModelProperty(name = "sign", value = "签名(签名规则:md5(md5(memberId) + memberId)) 例子: 123456 加密后: 5f1d7a84db00d2fce00b31a7fc73224f", required = true,example = "5f1d7a84db00d2fce00b31a7fc73224f")
    @NotNull(message = "签名不能为空")
    private String sign;
}
