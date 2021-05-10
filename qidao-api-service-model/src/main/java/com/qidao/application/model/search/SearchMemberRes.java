package com.qidao.application.model.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("搜索用户结果返回对象")
public class SearchMemberRes {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String headImage;

    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    @ApiModelProperty(value = "所属单位")
    private String belong;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "关注状态 true-已关注 false-未关注")
    private boolean subscribe;

}
