package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/11 4:40 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobSetMemberMuteWithChatRoomRes", description = "[响应]添加禁言")
public class EasemobSetMemberMuteWithChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "result", value = "操作结果；true：添加成功；false：添加失败", example = "true")
    private Boolean result;

    @ApiModelProperty(name = "expire", value = "禁言到期的时间戳（从1970年1月1日开始的毫秒数。如果禁言时间传的值为“-1”，那么该时间戳为固定的4638873600000，请参考mute_duration参数的说明）", example = "-1")
    private Long expire;

    @ApiModelProperty(name = "user", value = "被禁言用户的 ID", example = "user1")
    private String user;

}
