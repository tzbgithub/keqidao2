package com.qidao.application.model.member.favor;

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
@ApiModel("删除收藏记录")
public class FavorDeleteReq {

    @ApiModelProperty(value = "动态ID" , required = true , example = "123")
    private Long dynamicId;

    @ApiModelProperty(value = "会员ID" , required = true , example = "123")
    private Long memberId;

}
