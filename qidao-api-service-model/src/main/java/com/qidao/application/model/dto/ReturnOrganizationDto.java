package com.qidao.application.model.dto;

import com.qidao.application.model.config.SelectConfigDTO;
import com.qidao.application.model.label.LabelDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "组织机构详情")
public class ReturnOrganizationDto extends BaseSelectConfigReturnDto {

    @ApiModelProperty(name = "name", value = "名称")
    private String name;
    @ApiModelProperty(name = "summary", value = "简介")
    private String summary;
    @ApiModelProperty(name = "industryRemark", value = "所属行业简介")
    private String industryRemark;
    @ApiModelProperty(name = "signTime", value = "注册时间")
    private LocalDateTime signTime;
    @ApiModelProperty(name = "license", value = "执照/铭牌 路径")
    private List<String> license;
    @ApiModelProperty(name = "qualifications", value = "证书/导师 资格证图片")
    private List<String> qualifications;
    @ApiModelProperty(name = "addressProvinceId", value = "地址省ID")
    private String addressProvinceId;
    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称")
    private String addressProvinceName;
    @ApiModelProperty(name = "addressCityId", value = "地址市id")
    private String addressCityId;
    @ApiModelProperty(name = "addressCityName", value = "地址市名称")
    private String addressCityName;
    @ApiModelProperty(name = "addressAreaId", value = "地址区ID")
    private String addressAreaId;
    @ApiModelProperty(name = "addressAreaName", value = "地址区名称")
    private String addressAreaName;
    @ApiModelProperty(name = "label", value = "标签", required = false)
    private List<Long> label;
    @ApiModelProperty(name = "belong", value = "所属单位", required = false)
    private String belong;
    @ApiModelProperty(name = "fundsId", value = "基金规模", required = false)
    private String fundsId;
    @ApiModelProperty(name = "email", value = "邮箱", example = "sdfsdfs@qq.com")
    private String email;
    @ApiModelProperty(name = "labelList", value = "标签列表")
    private List<LabelDTO> labelList;
    @ApiModelProperty(name = "industryList", value = "行业列表")
    private List<SelectConfigDTO> industryList;

}
