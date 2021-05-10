package com.qidao.application.model.easemob.chatfile;

import com.qidao.application.model.easemob.EasemobBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UploadRes extends EasemobBaseDTO {
    /*
    share-secret	上传成功后返回，发送消息时需要用到
     */
    @ApiModelProperty(value = "文件唯一ID，指定是哪个文件，发送消息时需要用到",required = true)
    private String uuid;

    @ApiModelProperty(value = "文件类型",required = true)
    private String type;

    /*
        不在 swagger 展示下列数据
     */
    @ApiModelProperty(name = "created", value = "创建时间", example = "1614581827789",hidden = true)
    private Long created;
    @ApiModelProperty(name = "nickname", value = "昵称", example = "test",hidden = true)
    private String nickname;
    @ApiModelProperty(name = "modified", value = "修改时间", example = "1614581827789",hidden = true)
    private String modified;
    @ApiModelProperty(name = "username", value = "用户名", example = "user002",hidden = true)
    private String username;
    @ApiModelProperty(name = "activated", value = "是否活跃", example = "true",hidden = true)
    private Boolean activated;
}
