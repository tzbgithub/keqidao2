package com.qidao.application.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServerIndustryRep {
    @ApiModelProperty(name = "id", value = "主键",required = true,example = "232423")
    private Long id;
    @ApiModelProperty(name = "pid", value = "父级",required = true,example = "表头")
    private Long pid;
    @ApiModelProperty(name = "val", value = "名称",required = true,example = "上海市")
    private String val;
    @ApiModelProperty(name = "sequence", value = "排序",required = true,example = "上海市")
    private String sequence;
    @ApiModelProperty(name = "serverIndustryRep", value = "子类",required = true,example = "上海市")
    private List<ServerIndustryRep> serverIndustryRep;

}
