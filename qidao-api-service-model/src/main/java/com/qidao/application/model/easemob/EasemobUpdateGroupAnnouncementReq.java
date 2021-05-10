package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/19 3:41 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateGroupAnnouncementReq", description = "[请求]获取群组公告")
public class EasemobUpdateGroupAnnouncementReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组id,不能为空", required = true, example = "143021205159938")
    private String groupId;

    @NotNull(message = "群组公告不能为null")
    @ApiModelProperty(name = "announcement", value = "群组公告", required = true, example = "本群是正规群，各位进群后请改一下昵称")
    private String announcement;

    public String getReqBodyJson(){
        return "{\"announcement\" : \"" + announcement + "\"}";
    }
}
