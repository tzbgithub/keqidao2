package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/19 3:56 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobGetGroupMembersReq", description = "[请求]分页获取群组成员")
public class EasemobGetGroupMembersReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "群组id,不能为空")
    @ApiModelProperty(name = "groupId", value = "群组id,不能为空", required = true, example = "143021205159938")
    private String groupId;

    @NotNull(message = "pageNum页码,不能为空")
    @Min(value = 1,message = "pageNum页码，最小值为1")
    @ApiModelProperty(name = "pageNum", value = "页码", required = true, example = "1")
    private Integer pageNum = 1;

    @NotNull(message = "pageSize页面大小,不能为空")
    @Min(value = 1,message = "pageSize页面大小，最小值为1")
    @ApiModelProperty(name = "pageSize", value = "页面大小", required = true, example = "1")
    private Integer pageSize = 1;
}
