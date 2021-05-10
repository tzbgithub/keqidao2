package com.qidao.application.model.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("根据type分页查询下拉选择请求参数对象")
public class SelectGetByTypeReq extends BasePageQuery {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型" , required = true , example = "4")
    private Integer type;

    @ApiModelProperty(value = "是否热门 0-否 1-是"  , example = "0")
    private Integer hot;

    @ApiModelProperty(value = "模糊搜索关键字",example = "关键字" , hidden = true)
    private String keyword;

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 11:00:00", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;

}
