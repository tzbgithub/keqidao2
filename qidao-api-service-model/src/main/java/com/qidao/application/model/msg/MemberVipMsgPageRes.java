package com.qidao.application.model.msg;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户vip消息响应对象")
public class MemberVipMsgPageRes{

    @ApiModelProperty(value = "消息ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态 0-（发送成功）未读 1-（发送成功）已读 2-未发送状态  3-发送成功 4-发送失败")
    private Integer status;

    @ApiModelProperty(value = "发送时间")
    private String sendTime;

}
