package com.qidao.application.model.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class MsgNotifyReq {
    @NotNull(message = "别名不能为空")
    @ApiModelProperty(value = "极光推送接收人别名，不需要添加 'qidao' 后缀",required = true)
    private String alias;

    @NotNull(message = "标题不能为空")
    @ApiModelProperty(value = "消息标题,因IOS和Android 不同，最好和 content 保持一致",required = true)
    private String title;

    @ApiModelProperty(value = "消息内容,因IOS和Android 不同，最好和 title 保持一致",required = false)
    private String content;
}
