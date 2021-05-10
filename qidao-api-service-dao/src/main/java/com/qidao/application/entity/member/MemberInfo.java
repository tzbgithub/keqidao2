package com.qidao.application.entity.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    private Long id;

    private String unionId;

    private String headImage;

    private String backendImage;

    private String name;

    private String education;

    private String industry;

    private String organizationName;

    private Long industryId;

    private String industryIds;

    private String industryNames;

    private String organization;

    private String cityName;

    private String areaName;

    private Long organizationId;

    private String position;

    private String belong;

    private Integer organizationType;

    private Integer level;

    private LocalDateTime vipEndTime;

    private Integer role;

    private String phone;

    private Integer verifyStatus;

    private Long teacherId;

    private String qualifications;
}
