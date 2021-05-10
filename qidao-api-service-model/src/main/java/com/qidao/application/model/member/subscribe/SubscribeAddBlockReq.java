package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/25 11:21 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SubscribeAddBlockReq", description = "[请求]添加单个屏蔽")
public class SubscribeAddBlockReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "请填入操作人")
    @ApiModelProperty(name = "memberId", value = "当前会员的用户id",required = true,example = "130879657672706")
    private Long memberId;

    @NotNull(message = "请指定要屏蔽的人")
    @ApiModelProperty(name = "subscribeId", value = "被屏蔽的会员的用户id",required = true,example = "130899165380610")
    private Long subscribeId;

    @NotNull(message = "屏蔽对象的身份不能为空")
    @Min(value = 0,message = "屏蔽对象的身份-值最小为0")
    @Max(value = 1,message = "屏蔽对象的身份-值最大为1")
    @ApiModelProperty(name = "subscribeType", value = "屏蔽对象的身份,0-会员,1-组织机构",required = true,example = "1")
    private Integer subscribeType;
}
