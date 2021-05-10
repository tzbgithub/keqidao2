package com.qidao.application.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.validator.CustomEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "OrganizationDto")
public class OrganizationDto implements Serializable {
    @ApiModelProperty(name = "name", value = "名称", required = true, example = "东华大学")
    @NotNull(message = "姓名 不能为空")
    @Size(max = 16, message = "姓名 最多 16 字符")
    private String name;

    @ApiModelProperty(name = "summary", value = "简介", required = true, example = "测试数据")
    @NotNull(message = "请提供简介信息")
    private String summary;

    @ApiModelProperty(name = "industryId", value = "所属行业id", required = true, example = "1339141476717829")
    private Long industryId;

    @Size(max = 10, message = "所选行业过多，请适当精简")
    @ApiModelProperty(name = "industryIds", value = "所属行业id", required = true, example = "[1339141476717829,1339141476717830,1339141476717831]")
    private List<Long> industryIds;

    @ApiModelProperty(name = "industryRemark", value = "所属行业简介", required = false, example = "测试信息")
    private String industryRemark;

    @ApiModelProperty(name = "scaleId", value = "规模id", required = true, example = "100")
    @NotNull(message = "请选择对应规模")
    private Long scaleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "signTime", value = "注册时间", required = false, example = "2020-12-15 14:15:17")
    private LocalDateTime signTime;

    @ApiModelProperty(name = "license", value = "执照/铭牌 路径", required = false, example = "ssss.xjpg")
    private String license;

    @ApiModelProperty(name = "qualifications", value = "证书/导师 资格证图片", required = false, example = "[1,2,3]")
    private List<String> qualifications;

    @ApiModelProperty(name = "addressProvinceId", value = "地址省ID", required = true, example = "1")
    private String addressProvinceId;

    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称", required = true, example = "上海")
    private String addressProvinceName;

    @ApiModelProperty(name = "addressCityId", value = "地址市id", required = true, example = "1")
    private String addressCityId;

    @ApiModelProperty(name = "addressCityName", value = "地址市名称", required = true, example = "上海")
    private String addressCityName;

    @ApiModelProperty(name = "addressAreaId", value = "地址区ID", required = true, example = "1")
    private String addressAreaId;

    @ApiModelProperty(name = "addressAreaName", value = "地址区名称", required = true, example = "上海")
    private String addressAreaName;

    @ApiModelProperty(name = "techScaleId", value = "技术规模id ", required = true, example = "1")
    private Long techScaleId;

    @ApiModelProperty(name = "verifyStatus", value = "0-未审核 1-审核中 2-审核后", required = false, example = "0")
    private Integer verifyStatus;

    @ApiModelProperty(name = "phone", value = "手机号", required = true, example = "18722390324")
    @Pattern(regexp = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))", message = "手机号码格式不正确,请核对后重新输入!")
    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "请输入标签")
    @ApiModelProperty(name = "label", value = "标签", required = true, example = "['人工智能','机器人']")
    private String[] label;

    @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "sdfsdfs@qq.com")
    @NotNull(message = "邮箱不能为空")
    @Size(max = 32, message = "邮箱最大32字符")
    @CustomEmail
    private String email;

    @ApiModelProperty(name = "createMan", value = "用户名称", required = true, example = "华盛顿")
    @Size(max = 16, message = "用户名称最大16字符")
    @NotNull(message = "请输入用户名称")
    private String createMan;

    @ApiModelProperty(name = "salesmanId", value = "销售人标识ID", required = false, example = "1245332")
    private Long salesmanId;

    @ApiModelProperty(name = "belong", value = "所属单位", required = true, example = "东华大学")
    @Size(max = 32, message = "所属单位最大32字符")
    @NotNull(message = "请输入所属单位")
    private String belong;

    @ApiModelProperty(name = "unionId", value = "微信标识", required = false, example = "324234234")
    String unionId;

    @ApiModelProperty(name = "positionId", value = "职位标识", required = false, example = "235235")
    private Long positionId;

    @ApiModelProperty(name = "educationId", value = "学历标识", required = false, example = "235235")
    private Long educationId;
   /* @ApiModelProperty(name = "roleId", value = "角色", required = false,example = "3")
    private Integer roleId;*/


}
