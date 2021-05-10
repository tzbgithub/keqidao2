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
 * @date : 2021/3/22 10:39 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobAddMemberToGroupBlockListBatchReq", description = "[请求]批量添加用户至群组黑名单")
public class EasemobAddMemberToGroupBlockListBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @Valid
    @NotEmpty(message = "添加的会员ID集合,不能为空")
    @ApiModelProperty(name = "usernames", value = "添加的会员ID", required = true, example = "[139715821633537, 139715838410753]")
    private List<
            @NotNull(message = "会员ID，不能为空")
            @Min(value = 1,message = "会员ID，不正确")
                    Long> usernames;

    public String getReqBodyJson(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"usernames\": [");
        for(Long id:usernames){
            sb.append("\"").append(id).append("\"").append(',');
        }
        sb.replace(sb.length()-1,sb.length(),"]}");
        return sb.toString();
    }
}
