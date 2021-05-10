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
 * @date : 2021/3/15 4:59 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveFriendReq", description = "[请求]移除好友")
public class EasemobRemoveFriendReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "当前会员ID，不能为空")
    @Min(value = 1,message = "当前会员ID，不正确")
    @ApiModelProperty(name = "ownerUsername", value = "当前IM用户名", required = true, example = "139573399191553")
    private Long ownerUsername;

    @NotNull(message = "好友会员ID，不能为空")
    @Min(value = 1,message = "好友会员ID，不正确")
    @ApiModelProperty(name = "friendUsername", value = "好友IM用户名", required = true, example = "139573711667201")
    private Long friendUsername;
}
