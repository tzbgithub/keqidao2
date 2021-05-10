package com.qidao.application.model.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: liu Le
 * @create: 2020-12-29 14:38
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "新增的分发渠道对象")
public class CanalCreateReq {

    @NotNull
    @ApiModelProperty(name = "createBy", value = "创建者ID", required = true, example = "130879657672706")
    private Long createBy;

    @NotNull
    @ApiModelProperty(name = "name", value = "渠道名",required = true, example = "文件上传")
    private String name;

    @NotNull
    @ApiModelProperty(name = "version", value = "版本号", required = true, example = "001")
    private String version;

    @NotNull
    @ApiModelProperty(name = "downPath", value = "下载路径", required = true, example = "www.baidu.com")
    private String downPath;
}
