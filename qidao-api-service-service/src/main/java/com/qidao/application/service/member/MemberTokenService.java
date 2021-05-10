package com.qidao.application.service.member;

import com.qidao.application.model.member.token.GeneratorAccessTokenReq;
import com.qidao.application.model.member.token.GeneratorRefreshTokenDTO;

/**
 * @author Autuan.Yu
 */
public interface MemberTokenService {
    /**
     * 获取 refresh token
     * @param req 生成 refresh token 入参
     * @return
     * @author Autuan.Yu
     */
    String generatorRefreshToken(GeneratorRefreshTokenDTO req);

    /**
     * 获取 refresh token 不保存到db
     * @param req 生成 refresh token 入参
     * @return
     * @author Autuan.Yu
     */
    String generatorRefreshTokenNotToDB(GeneratorRefreshTokenDTO req);

    /**
     * 获取 access toke
     * 使用被逻辑删除的 refreshToken 也可以获得 accessToken
     * @param req 生成 access token 入参 {@link GeneratorAccessTokenReq}
     * @return
     */
    String generatorAccessToken(GeneratorAccessTokenReq req);

    /**
     * 更新token信息到数据库
     */
    void updateTokenToDb();

    /**
     * 删除长时间未使用的 refresh token （逻辑删除)
     */
    void logicDelTokenScheduled();
}
