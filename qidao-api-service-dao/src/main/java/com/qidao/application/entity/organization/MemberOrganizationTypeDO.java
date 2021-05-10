package com.qidao.application.entity.organization;

import lombok.Data;

@Data
public class MemberOrganizationTypeDO {
    private Long memberId;
    private String headImage;
    private String name;
    private String imEasemobUsername;

    private Long organizationId;
    private Integer type;
}
