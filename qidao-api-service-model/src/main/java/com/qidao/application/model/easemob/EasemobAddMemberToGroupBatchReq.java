package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/19 4:30 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupBatchReq", description = "[请求]批量添加群组成员")
public class EasemobAddMemberToGroupBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组id,不能为空", required = true, example = "143021205159938")
    private String groupId;

    @Valid
    @NotEmpty(message = "添加的会员ID集合,不能为空")
    @ApiModelProperty(name = "usernames", value = "添加的会员ID", required = true, example = "[139715762913281, 139715813244929]")
    private List<
            @NotNull(message = "会员ID，不能为空")
            @Min(value = 1,message = "会员ID，不正确")
                    Long> usernames;

    public String getReqBodyJson(){
        return "{\"usernames\": "+new Gson().toJson(usernames)+"}";
    }
}
