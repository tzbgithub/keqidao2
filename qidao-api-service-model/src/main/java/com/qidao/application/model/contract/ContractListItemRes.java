package com.qidao.application.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 1:47 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractListItemRes", description = "[响应]合同(我的项目)列表项")
public class ContractListItemRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "contractId", value = "合同项目id")
    private Long contractId;

    @ApiModelProperty(name = "serverTitle", value = "合同对应的源需求的标题")
    private String serverTitle;

    @ApiModelProperty(name = "organizationName", value = "合同甲方的所属组织机构名称")
    private String organizationName;

    @ApiModelProperty(name = "organizationName", value = "合同签订时间")
    private LocalDateTime signTime;

    @ApiModelProperty(name = "organizationName", value = "合同到期时间")
    private LocalDateTime endTime;

    @ApiModelProperty(name = "progressStep", value = "项目进程名(合同里最近更新的一项)")
    private String progressStep;

    @ApiModelProperty(name = "progressStatus", value = "项目进程任务状态(合同里最近更新的一项)，进度状态：0-未确认；1-已确认待完成；2-已完成待验收；3-已验收；4-超时完成")
    private Integer progressStatus;

    @ApiModelProperty(name = "contractStatus", value = "合同状态：0-草稿；1-已确定合同(进度已确定)；2-已签订合同(合同生效)；3-进度已完成")
    private Integer contractStatus;

    @ApiModelProperty(name = "role", value = "当前用户在合同中充当的角色(0-甲方，1-乙方)")
    private int role;
}
