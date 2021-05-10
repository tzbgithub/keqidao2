package com.qidao.application.model.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @Description:
 * @author: liu Le
 * @create: 2020-12-29 16:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CanalRes {

    @ApiModelProperty(name = "id", value = "分发渠道ID")
    private Long id;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateTime", value = "上一次更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "createBy", value = "创建者ID")
    private Long createBy;

    @ApiModelProperty(name = "updateBy", value = "更新者ID")
    private Long updateBy;

    @ApiModelProperty(name = "name", value = "渠道名")
    private String name;

    @ApiModelProperty(name = "version", value = "版本号")
    private String version;

    @ApiModelProperty(name = "downPath", value = "下载路径")
    private String downPath;
}
