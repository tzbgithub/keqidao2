package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @date : 2021/3/23 10:36 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupBlockListBatchReq", description = "[请求]移除群管理员")
public class EasemobRemoveMemberFromGroupBlockListBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @Valid
    @NotEmpty(message = "移除黑名单的会员ID集合,不能为空")
    @ApiModelProperty(name = "usernames", value = "移除黑名单的会员ID集合", required = true, example = "[139715857285121, 139715838410753]")
    private List<
            @NotNull(message = "会员ID，不能为空")
            @Min(value = 1,message = "会员ID，不正确")
                    Long> usernames;

    public String getUsernames(){
        StringBuilder sb = new StringBuilder();
        for(Long id:usernames){
            sb.append(id).append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
