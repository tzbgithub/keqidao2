package com.qidao.application.model.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 2:41 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LogErrorInsertBatchReq", description = "[请求]批量插入(异常、崩溃信息)")
public class LogErrorInsertBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @Valid
    @ApiModelProperty(name = "errorMessageList", value = "异常、崩溃信息(集合)")
    private List<ErrorMessage> errorMessageList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorMessage{
        @ApiModelProperty(name = "memberId", value = "当前会员的用户id",example = "139715802759169")
        private Long memberId;

        @ApiModelProperty(name = "vipLevel", value = "vip级别",example = "1")
        private Integer vipLevel;

        @Length(max = 32,message = "异常摘要exceptionSummary，不可超过32字")
        @NotNull(message = "异常摘要exceptionSummary,不能为空")
        @NotEmpty(message = "异常摘要exceptionSummary,不能为空")
        @ApiModelProperty(name = "exceptionSummary", value = "异常摘要(不可超过32字)",example = "假装是异常摘要",required = true)
        private String exceptionSummary;

        @NotNull(message = "异常记录时间exceptionTime,不能为空")
        @ApiModelProperty(value = "异常记录时间 格式：yyyy-MM-dd HH:mm:ss",example = "2021-01-01 09:00:00",required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime exceptionTime;

        @Length(max = 16,message = "版本version，不可超过16字")
        @ApiModelProperty(name = "version", value = "版本(不可超过16字)",example = "1.0.0")
        private String version;

        @Length(max = 16,message = "提交渠道channel，不可超过16字")
        @ApiModelProperty(name = "channel", value = "提交渠道(不可超过16字)",example = "channel_test")
        private String channel;

        @Length(max = 16,message = "提交机器machine，不可超过16字")
        @ApiModelProperty(name = "machine", value = "提交机器(不可超过16字)",example = "test001")
        private String machine;

        @NotNull(message = "异常描述exceptionDescription,不能为空")
        @NotEmpty(message = "异常描述exceptionDescription,不能为空")
        @ApiModelProperty(name = "exceptionDescription", value = "异常描述",example = "假装异常描述",required = true)
        private String exceptionDescription;
    }

}
