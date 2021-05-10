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
 * @date : 2021/3/10 10:39 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateChatRoomRes", description = "[响应]修改聊天室信息")
public class EasemobUpdateChatRoomRes implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "groupname", value = "聊天室名称，true表示修改成功，false表示修改失败,null说明该字段在请求中未传递", example = "true")
    private Boolean groupname;

    @ApiModelProperty(name = "description", value = "聊天室描述，true表示修改成功，false表示修改失败,null说明该字段在请求中未传递", example = "true")
    private Boolean description;

    @ApiModelProperty(name = "maxusers", value = "聊天室成员最大数（包括聊天室创建者），true表示修改成功，false表示修改失败,null说明该字段在请求中未传递", example = "true")
    private Boolean maxusers;

}
