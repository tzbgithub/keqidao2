package com.qidao.application.model.cos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UploadVideoRes {
    @ApiModelProperty("视频播放路径")
    private String videoPath;
    @ApiModelProperty("缩略图路径")
    private String thumbPath;
}
