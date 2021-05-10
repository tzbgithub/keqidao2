package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDataDto {

    @ApiModelProperty(name = "name", value = "名称")
    private String name;
    @ApiModelProperty(name = "summary", value = "简介")
    private String summary;
    @ApiModelProperty(name = "industryRemark", value = "所属行业简介")
    private String industryRemark;
    @ApiModelProperty(name = "signTime", value = "注册时间")
    private LocalDateTime signTime;
    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称")
    private String addressProvinceName;;
    @ApiModelProperty(name = "addressCityName", value = "地址市名称")
    private String addressCityName;
    @ApiModelProperty(name = "addressAreaName", value = "地址区名称")
    private String addressAreaName;
    @ApiModelProperty(name = "skillService", value = "服务方向 传入下拉标识")
    List<Long> skillService;
    @ApiModelProperty(name = "label", value = "标签", required = false)
    List<Long> label;
    @ApiModelProperty(name = "fundsId", value = "基金规模", required = false)
    String fundsId;

}
