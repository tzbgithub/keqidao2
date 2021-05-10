package com.qidao.application.service.member;

import com.qidao.application.model.member.favor.FavorDeleteReq;
import com.qidao.application.model.member.favor.FavorInsertReq;

public interface FavorService {


    /**
     * 新增收藏
     * @param req
     * @return
     */
    Boolean create(FavorInsertReq req);


    /**
     * 跟据动态id删除收藏
     * @param req
     */
    void deleteByDynamicId(FavorDeleteReq req);

    /**
     * 清空所有收藏
     * @param memberId
     */
    void empty(Long memberId);
}
