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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberBasicReq {
    @NotNull(message = "用户标识不能为空")
    @ApiModelProperty(name = "id", value = "会员主键", required = true,example = "234234234234234")
    private Long id;

    @ApiModelProperty(name = "name", value = "用户名", required = false,example = "234234234234234")
    @Length(max = 16,message = "长度不能超过16")
    private String name;

    @ApiModelProperty(name = "handUrl", value = "头像", required = false,example = "234234234234234")
    private String handUrl;

    @ApiModelProperty(name = "positionId", value = "职位标识", required = false,example = "234234234234234")
    private Long positionId;

    @ApiModelProperty(name = "email", value = "邮箱", required = false,example = "234234234234234")
    @Size(max = 32,message = "邮箱最大32字符")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 10 , message = "所选行业过多，请适当精简")
    @ApiModelProperty(value = "行业ID" ,example = "[123,345,677]")
    private List<Long> industryIds;
}
