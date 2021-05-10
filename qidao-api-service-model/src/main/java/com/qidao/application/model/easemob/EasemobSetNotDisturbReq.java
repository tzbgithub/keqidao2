package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/4 4:57 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobSetNotDisturbReq", description = "[请求]设置免打扰")
public class EasemobSetNotDisturbReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "需要设置免打扰的用户ID", required = true, example = "139715838410753")
    private Long username;

    @NotNull(message = "notificationNoDisturbing/是否免打扰,不能为空")
    @Range(max = 1L, message = "notificationNoDisturbing是否免打扰,只能是0或1")
    @ApiModelProperty(name = "notificationNoDisturbing", value = "是否免打扰，“0”代表免打扰关闭，“1”免打扰开启", required = true, example = "0")
    private Integer notificationNoDisturbing;

    @Range(max = 24L, message = "notificationNoDisturbingStart免打扰的开始时间,0~24")
    @ApiModelProperty(name = "notificationNoDisturbingStart", value = "免打扰的开始时间。数字代表开始时间，例如“8”代表每日8:00开启免打扰", example = "8")
    private Integer notificationNoDisturbingStart;

    @Range(max = 24L, message = "notificationNoDisturbingEnd免打扰的结束时间,0~24")
    @ApiModelProperty(name = "notificationNoDisturbingEnd", value = "免打扰的结束时间。数字代表结束时间，例如“18”代表每日18:00关闭免打扰", example = "18")
    private Integer notificationNoDisturbingEnd;

    public String openJson() {
        return "{\"notification_no_disturbing\": true,\"notification_no_disturbing_start\": \""
                + notificationNoDisturbingStart + "\",\"notification_no_disturbing_end\": \""
                + notificationNoDisturbingEnd
                + "\"}";
    }

    public String closeJson() {
        return "{\"notification_no_disturbing\": false}";
    }
}
