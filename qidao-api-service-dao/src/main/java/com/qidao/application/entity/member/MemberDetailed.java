package com.qidao.application.entity.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailed {
    private Long id;

    private String unionId;

    private String headImage;

    private String backendImage;

    private String name;

    private String education;

    private Long educationId;

    private String industry;

    private String industryIds;

    private String industryNames;

    private Long industryId;

    private String organizationName;

    private String position;

    private Long positionId;

    private String belong;

    private String email;

    private String direction;

    private Integer organizationType;

    private Integer level;

    private LocalDateTime vipEndTime;

    private LocalDateTime organizationVipEndTime;

    private Integer role;

    private String phone;

    private String organizationId;

    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;

    private Integer verifyStatus;
    private String imEasemobUsername;

    private String qualifications;

}
