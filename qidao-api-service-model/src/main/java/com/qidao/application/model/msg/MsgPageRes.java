package com.qidao.application.model.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询消息响应对象")
public class MsgPageRes {

    @ApiModelProperty(value = "消息ID")
    private Long id;

    @ApiModelProperty(value = "菜单类型ID")
    private Long menuId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息类型  0-不推送 1-立即推送 2-每日推送 3-定时发送 4-触发类型：用户注册 5-滚动消息：达成合作 6-用户获取短信验证码")
    private Integer type;

    @ApiModelProperty(value = "消息状态 0-未发送 1-已发送 2-发送失败 -1:无需此字段" )
    private Integer status;

    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "推送时间")
    private LocalDateTime pushTime;

    @ApiModelProperty(value = "排序值 越大越前")
    private Integer sequence;

    @ApiModelProperty(value = "接收类型 0-全平台发送 1-指定用户发送" )
    private Integer receiveType;

    @ApiModelProperty(value = "用户最大可接收同一消息条数：0：不限制")
    private Integer maxNum;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "内容类型  0-原文发送 1-验证码")
    private Integer contentType;

    @ApiModelProperty(value = "标题类型 0-原文发送 1-验证码")
    private Integer titleType;

}
