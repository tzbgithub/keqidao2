package com.qidao.application.model.dynamic;

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
@ApiModel("根据发布类型获取实验室动态请求参数")
public class OrganizationDynamicArticleReq extends BasePageQuery {

    @ApiModelProperty(value = "模糊搜索关键字",example = "关键字" ,hidden = true)
    private String keyword;

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 11:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss",example = "2021-01-01 18:00:00" , hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;

    @NotNull(message = "组织ID不能为空")
    @ApiModelProperty(value = "组织ID" , example = "139714302640130" , required = true)
    private Long organizationId;

    @NotNull(message = "发布类型ID不能为空")
    @ApiModelProperty(value = "发布类型ID" , example = "17930902" , required = true)
    private Long articleId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID" , example = "123" , required = true)
    private Long memberId;

}
