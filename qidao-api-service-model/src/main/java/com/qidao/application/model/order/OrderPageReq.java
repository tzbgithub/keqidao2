package com.qidao.application.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.common.req.BasePageQuery;
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
@ApiModel("查询订单请求对象")
public class OrderPageReq extends BasePageQuery {

    @ApiModelProperty(value = "模糊搜索关键字",example = "关键字" , hidden = true)
    private String keyword;

    @ApiModelProperty(value = "实物订单 0-否 1-是" , example = "0")
    private Integer physicalFlag;

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 11:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;

    @NotNull(message = "会员ID不能为空")
    @ApiModelProperty(value = "会员ID")
    private Long memberId;

}
