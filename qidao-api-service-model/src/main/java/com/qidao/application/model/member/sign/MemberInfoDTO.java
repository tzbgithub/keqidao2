package com.qidao.application.model.member.sign;

import com.qidao.application.model.member.MemberIndustryRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDTO {
    private Long id;

    private String no;

    private Integer level;

    private Long organizationId;

    private Integer role;

    private Long positionId;

    private Long educationId;

    private String phone;

    private Integer pushStatus;

    private String belong;

    private String headImage;

    private String backendImage;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private Long industryId;

    private String name;

    private String email;

    private String unionId;
    private Boolean  flag;
    private Integer  organizationType;
    private String imEasemobUsername;
    private String password;

    /**
     * 为所属组织机构 vip时间，在缓存中及响应结果都需响应/保存 此值
     */
    private LocalDateTime organizationVipEndTime;

    private List<MemberIndustryRes> memberIndustry;
}
