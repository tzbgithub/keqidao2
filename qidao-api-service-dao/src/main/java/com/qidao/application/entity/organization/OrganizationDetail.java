package com.qidao.application.entity.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetail {

    private String name;

    private String belong;

    private String summary;

    private String license;

    private Integer status;

    private String vipStartTime;

    private String vipEndTime;

    private String signTime;

    private String addressDetail;

    private String provinceName;

    private String cityName;

    private String areaName;

    private String industryRemark;

    private String backendImage;

    private String scaleName;

    private String label;

    private String industry;

    private Long scaleId;

}
