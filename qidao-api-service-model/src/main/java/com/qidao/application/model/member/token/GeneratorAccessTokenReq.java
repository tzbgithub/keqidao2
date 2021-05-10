package com.qidao.application.model.member.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Autuan.Yu
 */
@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorAccessTokenReq {
    @NotNull(message = "refreshToken 不能为空")
    @ApiModelProperty(value = "刷新token:登录接口返回",required = true,example = "b7eb44c14dbdf4f99d9eba2009e841421ddbdb8f85d9bcc34912ed28af447a24afa780dfb3d0516d0522d021f60ae53167d049d6647a5cac47d6d78cf4dab3776d399ca876a41547f36d300ca0ab638ba53c7f1b2bb0bff27ad1450c564c83fba239d975e728fb2a9df9e3c4bdb821a5c68b3b731a66d3ec88fa1baa638c266f9b8e0cabdffd5f2a30d6f91e2b39364274b59acb1c8f78426979df8c7948c20400bc5aeadb240f58")
    @Size(min = 1,message = "refreshToken 不能为空")
    private String refreshToken;
}
