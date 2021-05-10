package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/15 1:32 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetGroupDetailByIdReq", description = "[请求]根据群组ID获取此群组的详情")
public class EasemobGetGroupDetailByIdReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "groupIds群组id集合,不能为空")
    @ApiModelProperty(name = "groupIds", value = "群组id集合", required = true, example = "[\"143021205159938\",\"143021232422913\"]")
    private List<String> groupIds;

    public String getGroupIds(){
        return String.join(",", groupIds);
    }
}
