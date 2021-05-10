package com.qidao.application.entity.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/13 3:40 PM
 */
@Data
public class ServerListDo {

    @ApiModelProperty(value = "模糊搜索关键字",example = "关键字")
    private String keyword;

    @ApiModelProperty(value = "分页查询的页码(第n页),从1计数",example = "1",required = true)
    @NotNull(message = "分页条件不能为空")
    private Integer offset;

    @ApiModelProperty(value = "一页的容量(页面共m条信息)",example = "10",required = true)
    @NotNull(message = "分页条件不能为空")
    private Integer limit;

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 11:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;
}
