package com.qidao.application.model.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("修改消息请求对象")
public class MsgUpdateReq {

    @NotNull(message = "消息ID不能为空")
    @ApiModelProperty(value = "消息ID" , required = true , example = "134516773552129")
    private Long id;

    @NotNull(message = "菜单类型ID不能为空")
    @ApiModelProperty(value = "菜单类型ID" , required = true , example = "321312")
    private Long menuId;

    @NotNull(message = "标题不能为空")
    @ApiModelProperty(value = "标题" , required = true , example = "1111")
    private String title;

    @NotNull(message = "消息类型不能为空")
    @ApiModelProperty(value = "消息类型  0-不推送 1-立即推送 2-每日推送 3-定时发送 4-触发类型：用户注册" , required = true , example = "0")
    @Max(value = 4,message = "类型不合法")
    @Min(value = 0,message = "类型不合法")
    private Integer type;

    @ApiModelProperty(value = "消息状态 0-未发送 1-已发送 2-发送失败" , example = "0")
    @Max(value = 1,message = "状态不合法")
    @Min(value = 0,message = "状态不合法")
    private Integer status;

    @ApiModelProperty(value = "过期时间 格式：yyyy-MM-dd HH:mm:ss",example = "2021-01-01 09:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "推送时间 格式：yyyy-MM-dd HH:mm:ss",example = "2021-01-01 09:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pushTime;

    @ApiModelProperty(value = "排序值 越大越前", example = "1")
    private Integer sequence;

    @ApiModelProperty(value = "接收类型 0-全平台发送 1-指定用户发送" , example = "0")
    private Integer receiveType;

    @ApiModelProperty(value = "用户最大可接收同一消息条数：0：不限制" , example = "0")
    private Integer maxNum;

    @NotNull(message = "内容不能为空")
    @ApiModelProperty(value = "内容" , required = true , example = "123")
    private String content;

    @ApiModelProperty(value = "内容类型 0-原文发送 1-验证码" , example = "0")
    @Size(min = 0 , message = "内容类型输入不合法")
    private Integer contentType;

    @ApiModelProperty(value = "标题类型 0-原文发送 1-验证码" , example = "0")
    @Size(min = 0 , message = "标题类型输入不合法")
    private Integer titleType;

}
