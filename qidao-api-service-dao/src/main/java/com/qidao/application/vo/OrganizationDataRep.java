package com.qidao.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class OrganizationDataRep extends  OrganizationBaseDataRep {
    @ApiModelProperty(name = "val", value = "标签")
    private List<String> valName;
    @ApiModelProperty(name = "val", value = "规模")
    private String scaleName;
    @ApiModelProperty(name = "val", value = "标签",hidden = true)
    private  String val;
}
