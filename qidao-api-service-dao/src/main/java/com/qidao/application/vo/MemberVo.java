package com.qidao.application.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVo {
    @ApiModelProperty(name = "id", value = "会员主键", required = false,example = "234234234234234")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间", required = false,example = "2020-02-02 22:22:22")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "organizationId", value = "组织标识", required = false,example = "234234234234234")
    private Long organizationId;

    @ApiModelProperty(name = "positionId", value = "职位标识", required = false,example = "1")
    private Long positionId;

    @ApiModelProperty(name = "scaleId", value = "规模id", required = false,example = "1")
    private Long scaleId;
    @ApiModelProperty(name = "scaleName", value = "规模", required = false,example = "1")
    private String scaleName;

    @ApiModelProperty(name = "educationId", value = "学历标识", required = false,example = "2")
    private Long educationId;

    @ApiModelProperty(name = "positionName", value = "职位", required = false,example = "1")
    private String positionName;

    @ApiModelProperty(name = "educationName", value = "学历", required = false,example = "1")
    private String educationName;

    @ApiModelProperty(name = "organizationName", value = "组织机构", required = false,example = "1")
    private String organizationName;

    @ApiModelProperty(name = "belong", value = "所属单位", required = false,example = "东华大学")
    private String belong;

    @ApiModelProperty(name = "headImage", value = "头像", required = false,example = "xxx.jjpg")
    private String headImage;

    @ApiModelProperty(name = "backendImage", value = "背景图片", required = false,example = "xxx.jjpg")
    private String backendImage;

    @ApiModelProperty(name = "backendImage", value = "用户名", required = false,example = "xxx.jjpg")
    @Length(max = 16,message = "长度不能超过16")
    private String name;

    @ApiModelProperty(name = "cityName", value = "市", required = false,example = "xxx.jjpg")
    private String cityName;

    @ApiModelProperty(name = "areaName", value = "区", required = false,example = "xxx.jjpg")
    private String areaName;

    @ApiModelProperty(name = "labelName", value = "标签名称", required = false,example = "xxx.jjpg")
    private List<String>  labelName;

    @ApiModelProperty(name = "serverSize", value = "承接项目数量")
    private  Integer serverSize;

    @ApiModelProperty(name = "memberDynamicCountVoList", value = "各类型动态数量")
    private List<MemberDynamicCountVo> memberDynamicCountVoList;

    private String no;

    private Integer level;

    private Integer role;

    private String phone;

    private Integer pushStatus;

    private String password;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Long industryId;

    private String email;

    private String imEasemobUsername;

    private Integer imEasemobActive;

    private Integer type;

    @ApiModelProperty(value = "导师Id")
    private Long teacherId;

}
