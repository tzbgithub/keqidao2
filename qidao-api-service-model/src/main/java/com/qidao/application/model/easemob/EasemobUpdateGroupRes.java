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
 * @date : 2021/3/15 3:53 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateGroupRes", description = "[响应]修改群组的部分信息")
public class EasemobUpdateGroupRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "description", value = "群组描述，true表示修改成功，false表示修改失败", example = "true")
    private Boolean description;

    @ApiModelProperty(name = "maxusers", value = "群组成员最大数，true表示修改成功，false表示修改失败", example = "true")
    private Boolean maxusers;

    @ApiModelProperty(name = "groupname", value = "群组名称，true表示修改成功，false表示修改失败", example = "true")
    private Boolean groupname;
}
