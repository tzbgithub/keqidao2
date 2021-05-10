package com.qidao.application.model.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("受邀请的用户")
public class InvitedMemberRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "昵称")
    private String name;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(value = "邀请时间")
    private LocalDateTime bindTime;

}
