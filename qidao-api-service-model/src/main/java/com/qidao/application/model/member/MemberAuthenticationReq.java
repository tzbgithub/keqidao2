package com.qidao.application.model.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthenticationReq {
    @ApiModelProperty(name = "memberId", value = "用户标识", required = true,example = "234234234234234")
    @NotNull(message = "目标对象不能为空")
    private Long memberId;
    @ApiModelProperty(name = "imgs", value = "认证图片", required = false,example = "[1,2,3]")
    private List<String> qualifications;
    @NotNull(message = "目标对象类型不能为空")
    @ApiModelProperty(name = "type", value = "组织机构类型", required = true,example = "234234234234234")
    private Integer type;
    @NotNull(message = "请上传营业执照信息")
    @ApiModelProperty(name = "license", value = "营业执照", required = true,example = "234234234234234")
    private List<String> license;
}
