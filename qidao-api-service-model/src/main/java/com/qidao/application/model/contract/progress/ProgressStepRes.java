package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/7 3:39 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProgressStepRes", description = "[响应]确认项目周期目标页列表项")
public class ProgressStepRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "项目进度任务目标id")
    private Long id;

    @ApiModelProperty(name = "beginTime", value = "项目进度时间-起始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(name = "endTime", value = "项目进度时间-截止时间")
    private LocalDateTime endTime;

    @ApiModelProperty(name = "step", value = "步骤名称(例:项目目标一)")
    private String step;

    @ApiModelProperty(name = "description", value = "进度任务描述(例:3D展示系统的指定和任务分配)")
    private String description;
}
