package com.qidao.application.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InfoOrganizationServicePageRep {
    @ApiModelProperty(name = "id", value = "服务标识",required = true,example = "232423")
    private Long id;
    @ApiModelProperty(name = "title", value = "标题名称",required = true,example = "表头")
    private String title;
    @ApiModelProperty(name = "addressProvinceName", value = "地址省名称",required = true,example = "上海市")
    private String addressProvinceName;
    @ApiModelProperty(name = "addressCityName", value = "地址市名称",required = true,example = "上海市")
    private String addressCityName;
    @ApiModelProperty(name = "specAreaId", value = "需求领域id",required = true,example = "1")
    private Long specAreaId;
    @ApiModelProperty(name = "specAmountId", value = "研究经费id",required = true,example = "1")
    private Long specAmountId;
    @ApiModelProperty(name = "val", value = "研究经费val",required = true)
    private String val;
    @ApiModelProperty(name = "specAreaName", value = "需求领域val",required = true)
    private String specAreaName;
    @ApiModelProperty(name = "description", value = "描述",required = true)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "solutionTime", value = "期望解决时间",required = true,example = "2020-02-02 22:22:22")
    private LocalDateTime solutionTime;
    @ApiModelProperty(name = "labelName", value = "标签",required = false)
    private List<String> labelName;

    private Integer verifyStatus;

    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoOrganizationServicePageRep that = (InfoOrganizationServicePageRep) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
