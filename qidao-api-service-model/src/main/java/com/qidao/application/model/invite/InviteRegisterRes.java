package com.qidao.application.model.invite;

import com.qidao.application.model.member.MemberIndustryRes;
import com.qidao.application.model.member.MemberLabelRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("邀请注册返回对象")
public class InviteRegisterRes {


    @ApiModelProperty(value = "生成的新用户ID")
    private Long memberId;

    @ApiModelProperty(value = "邀请人id")
    private Long inviteMemberId;

    @ApiModelProperty(value = "邀请类型")
    private Integer type;

    @ApiModelProperty(value = "行业")
    private List<MemberIndustryRes> industry;

    @ApiModelProperty(value = "标签")
    private List<MemberLabelRes> label;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
