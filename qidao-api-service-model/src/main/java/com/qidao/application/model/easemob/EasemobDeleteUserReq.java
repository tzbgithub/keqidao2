package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/3 2:03 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobDeleteUserReq", description = "[请求]删除单个用户")
public class EasemobDeleteUserReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "被删除的用户ID", required = true, example = "139715838410753")
    private Long username;
}
