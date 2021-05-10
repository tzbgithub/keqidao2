package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/3 11:23 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRegisterBatchReq", description = "[请求]批量注册用户(授权)")
public class EasemobRegisterBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(message = "需要注册的用户(对象数组)")
    @ApiModelProperty(name = "EasemobUsers", value = "注册的IM用户数组", required = true, example = "[{\"nickname\": \"隔壁老王\",\"password\": \"j2ilMJII0d3UM3yJ001\",\"username\": \"139715838410753\"}]")
    private List<EasemobRegisterBatchReq.User> users;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        @NotNull(message = "username,会员ID，不能为空")
        @Min(value = 1, message = "username,会员ID，不正确")
        @ApiModelProperty(name = "username", value = "会员ID", required = true, example = "139715838410753")
        private Long username;

        @NotNull(message = "password/登录密码,不能为空")
        @Length(max = 64, message = "password不能超过64字符")
        @ApiModelProperty(name = "password", value = "登录密码(长度不超过64字符)", required = true, example = "j2ilMJII0d3UM3yJ")
        private String password;

        @Length(max = 100, message = "昵称不可超过100字符")
        @ApiModelProperty(name = "nickname", value = "昵称（可选,长度不超过100字符）", example = "隔壁老王")
        private String nickname;
    }

}
