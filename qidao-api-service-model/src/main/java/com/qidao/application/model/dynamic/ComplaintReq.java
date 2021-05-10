package com.qidao.application.model.dynamic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: liu Le
 * @create: 2020-12-25 15:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("客服处理投诉对象")
public class ComplaintReq {

    @NotNull
    @ApiModelProperty(name = "id", value = "投诉id", required = true, example = "392313164903092224")
    private Long id;

    @ApiModelProperty(name = "updateTime", value = "投诉处理时间", required = false, example = "2020-12-18 13:54:38")
    private Data updateTime;

    @ApiModelProperty(name = "delFlag", value = "是否删除 0-否 1-是", required = false, example = "0")
    private Byte delFlag;

    @ApiModelProperty(name = "status", value = "客服处理状态 0-未处理 1-处理中 2-已处理", required = false, example = "0")
    private Integer status;

    @ApiModelProperty(name = "reply", value = "客服回复内容", required = true, example = "您的投诉请求已受理，感谢您的宝贵建议，我们将严格限制此类内容")
    private String reply;

    @ApiModelProperty(name = "replyId", value = "回复客服id", required = true, example = "388559206292262912")
    private Long replyId;

    @ApiModelProperty(name = "replyUserName", value = "回复客服名", required = true, example = "张三39")
    private String replyUserName;
}
