package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/22 9:33 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobTransferGroupReq", description = "[请求]转让群组")
public class EasemobTransferGroupReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @NotNull(message = "newowner,会员ID，不能为空")
    @Min(value = 1,message = "newowner,会员ID，不正确")
    @ApiModelProperty(name = "newowner", value = "群组新管理员的会员ID", required = true, example = "139715762913281")
    private Long newowner;

    public String getReqBodyJson(){
        return "{\"newowner\": \""+newowner+"\"}";
    }
}
