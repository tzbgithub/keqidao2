package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 1:50 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProgressAcceptReq", description = "[请求]甲方-验收项目内的某项'已完成'的进度任务")
public class ProgressAcceptReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(name = "memberId", value = "甲方的会员用户id",required = true,example = "130899165380610")
    private Long memberId;

    @NotNull
    @ApiModelProperty(name = "progressId", value = "已完成且待验收的项目进度任务id",required = true,example = "1")
    private Long progressId;
}
