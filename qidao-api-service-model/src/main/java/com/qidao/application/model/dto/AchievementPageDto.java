package com.qidao.application.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@ApiModel
public class AchievementPageDto extends BasePageQuery {

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 09:00:00",hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00",hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;
    @ApiModelProperty(value = "模糊搜索关键字",example = "关键字",hidden = true)
    private String keyword;
    @NotNull(message = "请传入组织标识")
    @ApiModelProperty(name = "organizationId",value = "组织标识",required = true ,example = "2332423423")
    private  Long organizationId;
    @ApiModelProperty(name = "类型", value = "type",example = "1")
    private  Long type;
}
