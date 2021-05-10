package com.qidao.application.model.msg;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("消息记录查询响应对象")
public class MsgRecordPageRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @NotNull(message = "消息ID不能为空")
    @ApiModelProperty(value = "消息ID" )
    private Long msgId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "状态 0-未读 1-已读")
    private Integer status;

    @ApiModelProperty(value = "重复次数 第n次：默认1")
    private Integer sendNum;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

}
