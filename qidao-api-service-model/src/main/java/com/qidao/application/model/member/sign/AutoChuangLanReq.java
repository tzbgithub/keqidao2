package com.qidao.application.model.member.sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoChuangLanReq {

        @ApiModelProperty(value = "登录方式",notes = " 1:微信第三方union id;",example = "1")
        private Integer type;
        private String ip;
        private String machineCode;
        private String version;
        private Integer canal;
        @ApiModelProperty(value = "token",notes = "token",required = true,example = "sdjljsldgjlasdn,asdnf,")
        @NotNull
        private  String token;
        @ApiModelProperty(value = "手机号加解密方式",notes = " 0 AES 1 RSA , 可以不传，不传则手机号解密直接使用AES解密;",required = false,example = "0")
        private  String encryptType;

}
