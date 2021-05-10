package com.qidao.application.model.member.assistant;

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
@ApiModel("助手实验室空间返回用户信息")
public class AssistantInfoRes {

    @ApiModelProperty(value = "老师")
    private AssistantBaseInfoDTO teacher;

    @ApiModelProperty(value = "同老师下助手")
    private List<AssistantBaseInfoDTO> assistant;

}
