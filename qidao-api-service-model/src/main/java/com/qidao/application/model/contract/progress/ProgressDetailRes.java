package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 上面的是progress的字段
 * 下面的是contract的字段
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/5 4:35 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProgressDetailRes", description = "[响应](合同)项目进度详情页")
public class ProgressDetailRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "beginTime", value = "项目进度时间-起始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(name = "endTime", value = "项目进度时间-截止时间")
    private LocalDateTime endTime;

    @ApiModelProperty(name = "title", value = "项目进度任务名称")
    private String title;

    @ApiModelProperty(name = "status", value = "进度状态：0-未确认；1-已确认待完成；2-已完成待验收；3-已验收；4-超时完成")
    private Integer status;

    @ApiModelProperty(name = "step", value = "步骤名称(例:项目目标一)")
    private String step;

    @ApiModelProperty(name = "description", value = "进度任务描述(例:3D展示系统的指定和任务分配)")
    private String description;

    @ApiModelProperty(name = "confirmTime", value = "项目确认时间")
    private LocalDateTime confirmTime;

    @ApiModelProperty(name = "doneTime", value = "项目完成时间")
    private LocalDateTime doneTime;

    @ApiModelProperty(name = "checkTime", value = "项目验收时间")
    private LocalDateTime checkTime;

    @ApiModelProperty(name = "checkTime", value = "项目需求(标题)")
    private String serverTitle;

    @ApiModelProperty(name = "organizationA", value = "甲方所在的组织名")
    private String organizationA;

    @ApiModelProperty(name = "organizationB", value = "乙方所在的组织名")
    private String organizationB;

    @ApiModelProperty(name = "memberNameA", value = "甲方的名称")
    private String memberNameA;

    @ApiModelProperty(name = "memberNameB", value = "乙方的名称")
    private String memberNameB;
}
