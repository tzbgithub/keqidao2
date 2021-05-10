package com.qidao.application.model.msg;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("新增消息记录请求对象")
public class MsgRecordInsertReq {

    @NotNull(message = "消息ID不能为空")
    @ApiModelProperty(value = "消息ID" , example = "134516773552129" , required = true)
    private Long msgId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , required = true , example = "134134370467842")
    private Long memberId;

    @ApiModelProperty(value = "发送时间" , required = true , example = "2021-01-01 09:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "重复发送次数默认为1" , required = true , example = "1")
    private Integer sendNum;

}
