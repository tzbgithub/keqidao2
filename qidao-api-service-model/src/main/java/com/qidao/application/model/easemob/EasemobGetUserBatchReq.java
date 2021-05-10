package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/23 3:24 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetUserBatchReq", description = "[请求]批量获取用户")
public class EasemobGetUserBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "limit列表项数目,不能为空")
    @Min(value = 1, message = "limit列表项数目,至少为1")
    @ApiModelProperty(name = "limit", value = "列表项数目", required = true, example = "2")
    private Integer limit;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "cursor", value = "游标(第一次请求后返回，之后需要携带，作用类似offset;第一次请求/获取第一页时，不要携带!!)", example = "Y2hhdGdyb3VwczplYXNlbW9iLWRlbW8vY2hhdGRlbW91aV8z")
    private String cursor;
}
