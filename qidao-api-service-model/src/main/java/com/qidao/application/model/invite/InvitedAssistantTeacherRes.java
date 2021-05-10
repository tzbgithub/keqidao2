package com.qidao.application.model.invite;

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
@ApiModel("实验室老师查询我邀请的人列表")
public class InvitedAssistantTeacherRes {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "标记 true-我的助手 false-老师")
    private boolean flag;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(value = "邀请时间")
    private LocalDateTime bindTime;

}
