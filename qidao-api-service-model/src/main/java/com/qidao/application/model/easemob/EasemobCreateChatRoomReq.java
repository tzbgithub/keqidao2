package com.qidao.application.model.easemob;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/8 2:22 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobCreateChatRoomReq", description = "[请求]创建一个聊天室")
public class EasemobCreateChatRoomReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "聊天室名称，不能为空")
    @ApiModelProperty(name = "name", value = "聊天室名称，此属性为必须的", required = true, example = "聊天室_001")
    private String name;

    @NotBlank(message = "聊天室描述，不能为空")
    @ApiModelProperty(name = "description", value = "聊天室描述，此属性为必须的", required = true, example = "该聊天室是一个测试房间")
    private String description;

    @Max(value = 5000, message = "聊天室成员最大数量（包括聊天室创建者）,最大值5000")
    @Min(value = 1, message = "聊天室成员最小数量（包括聊天室创建者）,最小值1")
    @ApiModelProperty(name = "maxusers", value = "聊天室成员最大数（包括聊天室创建者），值为数值类型，默认值200，最大值5000，此属性为可选的", example = "200")
    private Integer maxusers = 200;

    @NotNull(message = "owner,聊天室管理员的会员ID，不能为空")
    @Min(value = 1, message = "owner,聊天室管理员的会员ID，不正确")
    @ApiModelProperty(name = "owner", value = "owner，聊天室管理员的会员ID,此属性为必须的", required = true, example = "139715813244929")
    private Long owner;


    @Size(min = 1, message = "聊天室成员(不包括房主)，至少一个")
    @ApiModelProperty(name = "members", value = "聊天室成员，此属性为可选的，但是如果加了此项，数组元素至少一个", example = "[139715838410753]")
    private List<

            @NotNull(message = "members,聊天室成员的会员ID，不能为空")
            @Min(value = 1, message = "members,聊天室成员的会员ID，不正确")
                    Long> members;


    public String getReqBodyJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"name\": \"").append(name)
                .append("\", \"description\": \"").append(description)
                .append("\", \"maxusers\": ").append(maxusers)
                .append(", \"owner\": \"").append(owner).append("\"");
        if(ObjectUtil.isNotEmpty(members)){
            sb.append(",\"members\": [");
            for(Long memberId:members){
                sb.append("\"").append(memberId).append("\",");
            }
            sb.setCharAt(sb.length()-1, ']');
        }
        sb.append("}");
        return sb.toString();
    }
}
