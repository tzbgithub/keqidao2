package com.qidao.application.model.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询消息请求对象")
public class MsgPageReq extends BasePageQuery {

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 09:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;

    @ApiModelProperty(value = "标题模糊搜索关键字",example = "关键字" )
    private String keyword;

    @ApiModelProperty(value = "消息类型",example = "1" )
    private Integer type;

    @ApiModelProperty(value = "菜单类型ID",example = "1231231" )
    private Long menuId;

    @ApiModelProperty(value = "内容模糊搜索关键字",example = "关键字" )
    private String content;

    @ApiModelProperty(value = "状态",example = "0" )
    private Integer status;

}
