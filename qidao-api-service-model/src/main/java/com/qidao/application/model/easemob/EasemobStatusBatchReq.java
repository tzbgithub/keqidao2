package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel
public class EasemobStatusBatchReq {
    @ApiModelProperty(value = "查询会员集合",example = "['1','2']")
    @NotNull(message = "查询用户不能为空")
    @Size(min = 1,max = 100,message = "查询范围：1-100条")
    private List<String> usernames;
}
