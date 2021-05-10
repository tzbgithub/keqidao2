package com.qidao.application.model.contract.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 待验证的信息
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/4 4:10 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProgressValidate implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    Long memberId;

    @JsonIgnore
    Long progressId;

    /**
     * 验证时，查询的contract中间产物
     * 项目对应的合同id
     */
    @JsonIgnore
    Long contractId;

    /**
     * 验证时，查询的progress中间产物
     * 项目进度任务截止时间
     */
    @JsonIgnore
    LocalDateTime endTime;

    /**
     * 验证时，查询的progress中间产物
     * 数据库中项目进度任务的状态
     */
    Integer progressStatus;

    /**
     * 表明需要是身份role_X才能执行后续操作
     * 例如 只有乙方才能执行 "完成" 操作，则role为B(乙方)
     */
    @JsonIgnore
    Integer role;

    /**
     * 表明正常情况下需要之前已经处于status_X，才能执行后续操作
     * 例如只有 "执行中的项目" 可以被 乙方 "完成"，则status = "执行中"
     */
    @JsonIgnore
    Integer[] preProgressStatus;

    /**
     * 非前置要求的status，而是可以"容忍"的项目进度状态
     * 考虑到可能反复发起 "完成" 的请求，导致数据库的status其实早已改变为 "完成" 却又反复申请修改状态为 "完成"，
     * 此时业务上不算异常，但是需要提醒用户不要反复操作。需要先于preProgressStatus进行校验
     */
    Integer[] allowdProgressStatus;
}
