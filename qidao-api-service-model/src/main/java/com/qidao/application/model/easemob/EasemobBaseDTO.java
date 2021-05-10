package com.qidao.application.model.easemob;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 环信响应用户信息
 */
@Data
public class EasemobBaseDTO {
    @ApiModelProperty(name = "created", value = "创建时间", example = "1614581827789")
    private Long created;
    @ApiModelProperty(name = "nickname", value = "昵称", example = "test")
    private String nickname;
    @ApiModelProperty(name = "modified", value = "修改时间", example = "1614581827789")
    private String modified;
    @ApiModelProperty(name = "type", value = "类别", example = "user")
    private String type;
    @ApiModelProperty(name = "uuid", value = "uuid", example = "56901d70-7a5b-11eb-8d28-b9a82b5248b0")
    private String uuid;
    @ApiModelProperty(name = "username", value = "用户名", example = "user002")
    private String username;
    @ApiModelProperty(name = "activated", value = "是否活跃", example = "true")
    private Boolean activated;
}
