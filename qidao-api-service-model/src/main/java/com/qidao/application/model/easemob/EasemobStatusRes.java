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
public class EasemobStatusRes {
    @ApiModelProperty(value = "用户名",example = "1234")
    private String username;

    @ApiModelProperty(value = "用户是否在线  true-在线  false-不在线",example = "true")
    private Boolean online ;

    public static EasemobStatusRes offline(String username){
        return new EasemobStatusRes(username,false);
    }
}
