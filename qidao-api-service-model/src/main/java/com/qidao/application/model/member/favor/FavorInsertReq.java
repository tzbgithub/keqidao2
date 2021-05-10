package com.qidao.application.model.member.favor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("新增收藏记录")
public class FavorInsertReq {

    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "会员ID" , required = true , example = "666")
    private Long memberId;

    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "动态ID" , required = true , example = "111")
    private Long dynamicId;

}
