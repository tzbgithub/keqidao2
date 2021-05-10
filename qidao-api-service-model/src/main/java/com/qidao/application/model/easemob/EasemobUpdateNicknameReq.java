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
 * @date : 2021/3/4 4:03 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateNickNameReq", description = "[请求]修改用户昵称")
public class EasemobUpdateNicknameReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "username,会员ID，不能为空")
    @Min(value = 1,message = "username,会员ID，不正确")
    @ApiModelProperty(name = "username", value = "会员ID", required = true, example = "139715838410753")
    private Long username;

    @NotNull(message = "nickname/昵称,不能为空")
    @Length(max = 100, message = "昵称不可超过100字符")
    @ApiModelProperty(name = "nickname", value = "昵称（可选,长度不超过100字符）", example = "隔壁老王")
    private String nickname;

    public String nicknameJson(){
        return "{\"nickname\": \""+nickname+"\"}";
    }

}
