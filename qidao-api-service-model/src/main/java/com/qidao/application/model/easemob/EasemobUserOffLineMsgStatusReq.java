package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class EasemobUserOffLineMsgStatusReq {
    @NotNull(message = "环信ID 不能为空")
    private String username;
    @NotNull(message = "消息ID 不能为空")
    private String msgId;
}
