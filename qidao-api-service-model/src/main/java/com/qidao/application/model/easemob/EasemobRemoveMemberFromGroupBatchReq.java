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
 * @date : 2021/3/19 5:06 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobRemoveMemberFromGroupBatchReq", description = "[请求]批量移除群组成员")
public class EasemobRemoveMemberFromGroupBatchReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId,群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组ID", required = true, example = "143021205159938")
    private String groupId;

    @Valid
    @NotEmpty(message = "删除的用户名称,不能为空")
    @ApiModelProperty(name = "members", value = "被删除的群组成员的会员ID集合", required = true, example = "[139715838410753, 139712941588481]")
    private List<
            @NotNull(message = "members,会员ID，不能为空")
            @Min(value = 1,message = "members,会员ID，不正确")
                    Long> members;

    public String getMembers(){
        StringBuilder sb = new StringBuilder();
        for(Long id:members){
            sb.append(id).append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
