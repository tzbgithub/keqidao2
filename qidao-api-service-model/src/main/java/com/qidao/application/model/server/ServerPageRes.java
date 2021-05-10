package com.qidao.application.model.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 1:47 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ServerPageRes", description = "[响应]需求列表项")
public class ServerPageRes {

    @ApiModelProperty(name = "id", value = "需求id")
    private Long id;

    @ApiModelProperty(name = "title", value = "需求标题")
    private String title;

    @ApiModelProperty(name = "addressProvinceName", value = "服务地址-省")
    private String addressProvinceName;

    @ApiModelProperty(name = "addressCityName", value = "服务地址-市")
    private String addressCityName;

    @ApiModelProperty(name = "specAmountName", value = "研究经费")
    private String specAmountName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(name = "solutionTime", value = "期望解决时间")
    private LocalDateTime solutionTime;

    @ApiModelProperty(name = "labelName", value = "标签集合")
    private List<String> labelName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "description", value = "需求描述(只截取前100字)")
    private String description;

}
