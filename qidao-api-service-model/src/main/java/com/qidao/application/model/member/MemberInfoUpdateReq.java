package com.qidao.application.model.member;

import com.qidao.application.model.validator.CustomEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("修改会员信息对象")
public class MemberInfoUpdateReq {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(name = "id" , value = "主键ID" ,required = true ,example = "132331855740930")
    private Long id;

    @ApiModelProperty(name = "headImage" , value = "头像" ,example = "")
    private String headImage;

    @ApiModelProperty(name = "backendImage" , value = "个人空间背景图",example = "")
    private String backendImage;

    @NotNull(message = "学历不能为空")
    @ApiModelProperty(name = "educationId" , value = "学历ID" , required = true,example = "1339141476717802")
    private Long educationId;

    @ApiModelProperty(name = "industryId" , value = "技术可应用行业ID" ,example = "1339141476717827")
    private Long industryId;

    @NotNull(message = "职称不能为空")
    @ApiModelProperty(name = "positionId" , value = "职称ID" , required = true,example = "1339141476717806")
    private Long positionId;

    @NotNull(message = "邮箱不能为空")
    @CustomEmail
    @ApiModelProperty(name = "email" , value = "邮箱",example = "sdfsdfs@qq.com")
    private String email;

    @ApiModelProperty(name = "labels" , value = "标签" ,example = "dss,asdasd")
    private String labels;

    @ApiModelProperty(name = "industryIds" , value = "技术可应用行业ID" ,example = "[1339141476717827,1339141476717888]")
    @Size(max = 10 , message = "所选行业过多，请适当精简")
    private List<Long> industryIds;
}
