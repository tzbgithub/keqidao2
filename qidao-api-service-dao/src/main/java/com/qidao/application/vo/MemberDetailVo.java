package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailVo {
    @ApiModelProperty(name = "id", value = "会员主键", required = true, example = "234234234234234")
    private Long id;

    @ApiModelProperty(name = "organizationId", value = "组织标识", required = true, example = "234234234234234")
    private Long organizationId;

    @ApiModelProperty(name = "organizationId", value = "组织名称")
    private String organizationName;

    @ApiModelProperty(name = "organizationType" ,value = "0-实验室 1-企业 null-没有加入组织机构")
    private Integer organizationType;

    @ApiModelProperty(name = "positionId", value = "职位标识", required = true, example = "1")
    private Long positionId;

    @ApiModelProperty(name = "educationId", value = "学历标识", required = true, example = "2")
    private Long educationId;

    @ApiModelProperty(name = "name", value = "用户名", required = true, example = "你好")
    @Length(max = 16, message = "长度不能超过16")
    private String name;

    @ApiModelProperty(name = "belong", value = "所属单位", required = true, example = "东华大学")
    private String belong;

    @ApiModelProperty(name = "headImage", value = "头像", required = true, example = "xxx.jjpg")
    private String headImage;

    @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "234234234234234")
    @NotNull(message = "邮箱不能为空")
    @Size(max = 32, message = "邮箱最大32字符")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(name = "label", value = "标签(手输入)", required = true, example = "['你好','动']")
    @NotNull(message = "标签不能为空")
    private List<String> label;

    @ApiModelProperty(name = "industryId", value = "行业标识", required = true, example = "1339141476717829")
    private Long industryId;

    @ApiModelProperty(name = "skillId", value = "技能服务(下拉选择)", required = false, example = "234234234234234")
    private List<Long> skillId;

    @ApiModelProperty(name = "positionName", value = "职位", required = false, example = "1")
    private String positionName;

    @ApiModelProperty(name = "educationName", value = "学历", required = false, example = "1")
    private String educationName;

    @ApiModelProperty(name = "industryName", value = "行业名称", required = true, example = "234234234234234")
    private String industryName;

    @ApiModelProperty(name = "industryNames", value = "行业名称", required = true, example = "234234234234234")
    private List<String> industryNames;

    @Size(max = 10 , message = "所选行业过多，请适当精简")
    @ApiModelProperty(name = "industryIds", value = "行业标识", required = true, example = "[1339141476717829,1339141476717830,1339141476717831]")
    private List<Long> industryIds;
}
