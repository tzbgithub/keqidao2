package com.qidao.application.model.easemob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/15 2:39 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EasemobCreateGroupReq", description = "[请求]创建一个新群组")
public class EasemobCreateGroupReq implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "groupname群组名称,不能为空")
    @ApiModelProperty(name = "groupname", value = "群组名称，此属性为必须的", required = true, example = "正规群")
    private String groupname;

    @NotBlank(message = "desc群组描述,不能为空")
    @ApiModelProperty(name = "desc", value = "群组描述，此属性为必须的", required = true, example = "大家注意了，这个是一个正规群")
    private String desc;

    @NotNull(message = "public是否是公开群，不能为空")
    @ApiModelProperty(name = "public", value = "是否是公开群，此属性为必须的", required = true, example = "true")
    @JsonProperty("public")
    @SerializedName("public")
    private Boolean isPublic;

    @Max(value = 2000, message = "maxusers群组成员最大数（包括群主）,最大值为2000")
    @Min(value = 1, message = "maxusers群组成员最大数（包括群主）,不能小于1")
    @ApiModelProperty(name = "maxusers", value = "群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的", example = "200")
    private Integer maxusers = 200;

    @ApiModelProperty(name = "approval", value = "加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true", example = "false")
    private Boolean approval = false;

    @NotNull(message = "owner,群组管理员的会员ID，不能为空")
    @Min(value = 1, message = "owner,群组管理员的会员ID，不正确")
    @ApiModelProperty(name = "owner", value = "owner，群组管理员的会员ID", required = true, example = "139715813244929")
    private String owner;

    @ApiModelProperty(name = "members", value = "群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个，不能超过100个（注：群主user1不需要写入到members里面）", example = "[139715813244929]")
    private List<
            @NotNull(message = "members,群组成员的会员ID，不能为空")
            @Min(value = 1, message = "members,群组成员的会员ID，不正确")
                    Long> members;

    public String getReqBodyJson() {
        return new Gson().toJson(this);
    }
}
