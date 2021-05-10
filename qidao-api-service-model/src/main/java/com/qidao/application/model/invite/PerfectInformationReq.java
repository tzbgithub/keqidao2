package com.qidao.application.model.invite;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("邀请完善信息接口")
public class PerfectInformationReq {

    @NotNull(message = "名称不能为空")
    @Size(max = 16 , message = "用户名称过长，请适当精简")
    @ApiModelProperty(value = "名称" , required = true , example = "123")
    private String name;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱" , required = true , example = "123@qq.com")
    private String email;

    @Size(max = 32 , message = "所属单位过长，请适当精简")
    @ApiModelProperty(value = "所属单位")
    private String belong;

    @Size(max = 16 , message = "组织机构名称过长，请适当精简")
    @ApiModelProperty(value = "组织机构名称" , example = "123")
    private String organizationName;

    @Size(max = 5 , message = "科研方向过多请适当精简")
    @ApiModelProperty(value = "标签" , example = "[1,2,3,4]")
    private List<String> labels;

    @Size(max = 10 , message = "行业过多 请适当精简")
    @ApiModelProperty(value = "行业" , example = "[1,2,3,4]")
    private List<Long> industryIds;

    @NotNull(message = "受邀请人ID不能为空")
    @ApiModelProperty(value = "受邀请人ID" , required = true , example = "123")
    private Long id;

    @ApiModelProperty(value = "邀请人ID")
    private Long inviteMemberId;

    @NotNull(message = "邀请类型不能为空")
    @ApiModelProperty(value = "邀请类型")
    private Integer type;

}
