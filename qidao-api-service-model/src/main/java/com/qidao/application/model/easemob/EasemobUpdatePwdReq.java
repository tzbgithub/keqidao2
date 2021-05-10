package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/3 2:48 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdatePwdReq", description = "[请求]修改用户密码")
public class EasemobUpdatePwdReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "会员ID", required = true, example = "139715838410753")
    private Long username;

    @NotNull(message = "newpassword/新登录密码,不能为空")
    @Length(max = 64, message = "newpassword不能超过64字符")
    @ApiModelProperty(name = "newpassword", value = "新登录密码(长度不超过64字符)", required = true, example = "j2ilMJII0d3UM3yJ")
    private String newpassword;

    public String newPasswordJson(){
        return "{\"newpassword\": \""+newpassword+"\"}";
    }
}
