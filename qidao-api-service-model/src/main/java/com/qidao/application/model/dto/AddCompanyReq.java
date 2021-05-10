package com.qidao.application.model.dto;

import com.qidao.application.model.validator.CustomEmail;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCompanyReq {

    @ApiModelProperty(name = "scaleId", value = "规模")
    private Long scaleId;

    @Size(max = 32, message = "名称最大32字")
    @ApiModelProperty(name = "name", value = "公司名称", required = true)
    @NotNull(message = "公司名称不能为空")
    private String name;

    @ApiModelProperty(name = "industryId", value = "所属行业标识")
    private List<Long> industryList;

    @ApiModelProperty(name = "addressProvinceId", value = "地址省ID", required = false, example = "1")
    private String addressProvinceId;

    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称", required = false, example = "上海")
    private String addressProvinceName;

    @ApiModelProperty(name = "addressCityId", value = "地址市id", required = false, example = "1")
    private String addressCityId;

    @ApiModelProperty(name = "addressCityName", value = "地址市名称", required = false, example = "上海")
    private String addressCityName;

    @ApiModelProperty(name = "addressAreaId", value = "地址区ID", required = false, example = "1")
    private String addressAreaId;

    @ApiModelProperty(name = "addressAreaName", value = "地址区名称", required = false, example = "上海")
    private String addressAreaName;

    @ApiModelProperty(name = "techScaleId", value = "技术规模id ", required = false, example = "1")
    private Long techScaleId;

    @ApiModelProperty(name = "license", value = "营业执照 ", required = false, example = "1")
    private List<String> license;

    @Size(max = 150, message = "简介长度不可超过150")
    @ApiModelProperty(name = "summary", value = "简介 ", required = false, example = "1")
    private String summary;

    @ApiModelProperty(name = "label", value = "标签", example = "['人工智能','机器人']")
    private String[] label;

    @ApiModelProperty(value = "会员ID", required = true)
    @NotNull(message = "请传入会员ID")
    @Min(value = 1L, message = "会员ID不合法")
    private Long memberId;

    @ApiModelProperty(name = "email", value = "邮箱(不能有中文)", required = true, example = "sdfsdfs@qq.com")
    @Size(max = 32, message = "邮箱最大32字符")
    @CustomEmail
    private String email;

}
