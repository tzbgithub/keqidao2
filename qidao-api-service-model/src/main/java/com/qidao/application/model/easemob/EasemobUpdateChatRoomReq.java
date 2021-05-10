package com.qidao.application.model.easemob;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/10 10:39 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateChatRoomReq", description = "[请求]修改聊天室信息")
public class EasemobUpdateChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "chatroomId聊天室Id,不能为空")
    @ApiModelProperty(name = "chatroomId", value = "聊天室ID", required = true, example = "142391087529986")
    private String chatroomId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "[^/]+", message = "聊天室名称，修改时值不能包含斜杠(“/”)")
    @ApiModelProperty(name = "name", value = "聊天室名称，修改时值不能包含斜杠(“/”)", required = true, example = "聊天室_001")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "[^/]+", message = "聊天室描述，修改时值不能包含斜杠(“/”)")
    @ApiModelProperty(name = "description", value = "聊天室描述，修改时值不能包含斜杠(“/”)", required = true, example = "该聊天室是一个测试房间")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Max(value = 5000, message = "聊天室成员最大数量（包括聊天室创建者）,最大值5000")
    @Min(value = 1, message = "聊天室成员最小数量（包括聊天室创建者）,最小值1")
    @ApiModelProperty(name = "maxusers", value = "聊天室成员最大数（包括聊天室创建者），值为数值类型，默认值200，最大值5000，此属性为可选的", example = "200")
    private Integer maxusers;

    public String getReqBodyJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (ObjectUtil.isNotNull(name)) {
            sb.append("\"name\": \"").append(name).append("\",");
        }
        if(ObjectUtil.isNotNull(description)){
            sb.append("\"description\": \"").append(description).append("\",");
        }
        if (ObjectUtil.isNotNull(maxusers)) {
            sb.append("\"maxusers\": ").append(maxusers).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
