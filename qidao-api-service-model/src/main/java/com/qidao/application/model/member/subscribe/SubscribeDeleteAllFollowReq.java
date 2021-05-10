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
 * @date : 2020/12/25 11:20 AM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SubscribeDeleteAllFollowReq", description = "[请求]清空所有关注")
public class SubscribeDeleteAllFollowReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户Id不能为空")
    @ApiModelProperty(name = "memberId", value = "当前会员的用户id",required = true,example = "139715909713921")
    private Long memberId;

    @NotNull(message = "关注对象的身份不能为空")
    @Min(value = 0,message = "关注对象的身份-值最小为0")
    @Max(value = 1,message = "关注对象的身份-值最大为1")
    @ApiModelProperty(name = "subscribeType", value = "关注对象的身份,0-会员,1-组织机构",required = true,example = "0")
    private Integer subscribeType;
}
