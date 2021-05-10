package com.qidao.application.model.member.sign;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SignInRes {
    // access token
    private String accessToken;
    // refresh token
    private String refreshToken;
    // 会员信息
    private MemberInfoDTO memberInfo;
}
