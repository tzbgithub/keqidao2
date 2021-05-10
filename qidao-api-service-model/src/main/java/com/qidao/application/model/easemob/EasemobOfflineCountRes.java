package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class EasemobOfflineCountRes {
    @ApiModelProperty(value = "用户名",example = "1234")
    private String username;

    @ApiModelProperty(value = "离线消息数量",example = "0")
    private Integer msgNum ;

}
