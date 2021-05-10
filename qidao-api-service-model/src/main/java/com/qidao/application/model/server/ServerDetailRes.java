package com.qidao.application.model.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 11:28 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ServerDetailRes", description = "[响应]需求详情页")
public class ServerDetailRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "title", value = "需求名称")
    private String title;

    @ApiModelProperty(name = "specArea", value = "需求领域")
    private String specArea;

    @ApiModelProperty(name = "specFund", value = "研发经费")
    private String specFund;

    @ApiModelProperty(name = "addressProvinceName", value = "服务地址-省")
    private String addressProvinceName;

    @ApiModelProperty(name = "addressCityName", value = "服务地址-市")
    private String addressCityName;

    @ApiModelProperty(name = "addressAreaName", value = "服务地址-区")
    private String addressAreaName;

    @ApiModelProperty(name = "createTime", value = "发布时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "solutionTime", value = "期望解决时间")
    private LocalDateTime solutionTime;

    @ApiModelProperty(name = "description", value = "需求描述")
    private String description;

    @ApiModelProperty(name = "thumb", value = "缩略图")
    private String thumb;

    @ApiModelProperty(name = "url", value = "图片或视频地址")
    private String url;

    @ApiModelProperty(name = "status", value = "需求状态类别：0-草稿；1-已发布；2-已接受；3-已取消")
    private Integer status;

    @ApiModelProperty(value = "甲方（发布方）组织ID")
    private Long organizedIdPartyA;

    @ApiModelProperty(value = "乙方（发布方）组织ID")
    private Long organizedIdPartyB;

    @ApiModelProperty(value = "甲方（发布者）用户ID")
    private Long memberIdPartyA;

    @ApiModelProperty(value = "乙方（发布者）用户ID")
    private Long memberIdPartyB;
}
