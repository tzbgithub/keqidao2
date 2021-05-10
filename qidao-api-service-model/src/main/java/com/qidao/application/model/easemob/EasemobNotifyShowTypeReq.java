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
 * @date : 2021/3/4 4:38 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobNotifyShowTypeReq", description = "[请求]设置推送消息展示方式")
public class EasemobNotifyShowTypeReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "会员ID", required = true, example = "139715838410753")
    private Long username;

    @NotNull(message = "notificationDisplayStyle/消息提醒方式,不能为空")
    @Range(max = 1L,message = "notificationDisplayStyle消息提醒方式,只能是0或1")
    @ApiModelProperty(name = "notificationDisplayStyle", value = "消息提醒方式，“0”仅通知，“1“通知以及消息详情", required = true, example = "0")
    private Integer notificationDisplayStyle;

    public String notificationDisplayStyleJson(){
        return "{\"notification_display_style\": \""+notificationDisplayStyle+"\"}";
    }

}
