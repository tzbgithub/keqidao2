package com.qidao.application.model.easemob;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/15 3:53 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobUpdateGroupReq", description = "[请求]修改群组的部分信息")
public class EasemobUpdateGroupReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupId")
    @ApiModelProperty(name = "groupId", value = "群组id,不能为空", required = true, example = "143021205159938")
    private String groupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "[^/]+", message = "groupname群组名称，修改时值不能包含斜杠(“/”)")
    @ApiModelProperty(name = "groupname", value = "groupname群组名称，修改时值不能包含斜杠(“/”)，有空格时需要用“+”代替", example = "正规群")
    private String groupname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "[^/]+", message = "description群组描述，修改时值不能包含斜杠（”/“）")
    @ApiModelProperty(name = "description", value = "群组描述，修改时值不能包含斜杠（”/“），有空格时需要用“+”代替", example = "这里是正规群，请注意言辞")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Max(value = 2000, message = "maxusers群组成员最大数（包括群主）,最大值为2000")
    @Min(value = 1, message = "maxusers群组成员最大数（包括群主）,不能小于1")
    @ApiModelProperty(name = "maxusers", value = "群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的", example = "500")
    private Integer maxusers;

    public String getReqBodyJson(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        if(!ObjectUtil.isEmpty(groupname)){
            sb.append("\"groupname\": \"").append(groupname).append("\",");
        }
        if(!ObjectUtil.isEmpty(description)){
            sb.append("\"description\": \"").append(description).append("\",");
        }
        if(ObjectUtil.isNotNull(maxusers)){
            sb.append("\"maxusers\": ").append(maxusers).append(",");
        }
        if(sb.length()>1){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append('}');
        return sb.toString();
    }
}
