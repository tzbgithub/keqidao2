package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description:
 * @author: liu Le
 * @create: 2020-12-25 14:50
 */
@Data
@Builder
@ApiModel("投诉对象")
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRes {

    @ApiModelProperty(name = "createTime", value = "投诉创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateTime", value = "上一次投诉更新时间，未处理为创建时间，处理过为处理时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "dynamicId", value = "被投诉动态ID")
    private Long dynamicId;

    @ApiModelProperty(name = "complaintUserName", value = "被投诉者用户名")
    private String complaintUserName;

    @ApiModelProperty(name = "memberUserName", value = "投诉者用户名")
    private String memberUserName;

    @ApiModelProperty(name = "reason", value = "投诉原因")
    private String reason;

    @ApiModelProperty(name = "status", value = "投诉处理状态：0-未处理 1-处理中 2-待处理")
    private Integer status;

    @ApiModelProperty(name = "level", value = "投诉者会员等级: 0-普通用户 1-VIP")
    private Integer level;
}
