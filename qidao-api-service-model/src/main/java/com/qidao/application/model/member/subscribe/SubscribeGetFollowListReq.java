package com.qidao.application.model.member.subscribe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/25 1:49 PM
 */
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SubscribeGetFollowListReq", description = "[请求]分页获取关注列表")
public class SubscribeGetFollowListReq implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 当前会员的用户id
     */
    @NotNull(message = "用户Id不能为空")
    @ApiModelProperty(name = "memberId", value = "当前会员的用户id", required = true, example = "142121059942402")
    private Long memberId;

    /**
     * 关注对象的身份,0-会员,1-组织机构
     */
    @NotNull(message = "关注对象的身份不能为空")
    @Min(value = 0,message = "关注对象的身份-值最小为0")
    @Max(value = 2,message = "关注对象的身份-值最大为2")
    @ApiModelProperty(name = "subscribeType",value = "关注对象的身份,0-会员,1-组织机构,2-仅实验室", required = true, example = "0")
    private Integer subscribeType;

    /**
     * 分页查询的页码(第n页),从1计数
     */
    @NotNull(message = "offset不能为空")
    @ApiModelProperty(name = "offset", value = "分页查询的页码(第n页),从1计数", required = true, example = "1")
    private Integer offset;

    /**
     * 一页的容量(页面共m条信息)
     */
    @NotNull(message = "limit不能为空")
    @ApiModelProperty(name = "limit", value = "一页的容量(页面共m条信息)", required = true, example = "1")
    private Integer limit;
}
