package com.qidao.application.model.member.feedback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class MemberComplaintAddReq {

    @NotNull(message = "投诉人不能为空")
    @ApiModelProperty(name = "memberId", value = "投诉者ID--主键", required = true, example = "134286401404930")
    private Long memberId;

    @NotNull(message = "被投诉人不能为空")
    @ApiModelProperty(name = "complaintMemberId", value = "被投诉人ID--主键", required = true, example = "134286401404930")
    private Long complaintMemberId;

    @NotNull(message = "投诉原因不能为空")
    @ApiModelProperty(name = "reasonId", value = "投诉原因ID--主键 ", required = true, example = "1339141476717920")
    private Long reasonId;

//    @ApiModelProperty(value = "投诉类型 0-投诉动态 1-投诉会员 默认0",example = "0")
//    private Integer type;
}
