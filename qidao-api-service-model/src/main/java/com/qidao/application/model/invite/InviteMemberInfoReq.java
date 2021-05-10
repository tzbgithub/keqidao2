package com.qidao.application.model.invite;

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
@ApiModel("邀请用户请求对象")
public class InviteMemberInfoReq {



    @NotNull(message = "短链接不能为空")
    @ApiModelProperty(value = "短链接" , required = true , example = "123")
    private String shortUrl;

}
