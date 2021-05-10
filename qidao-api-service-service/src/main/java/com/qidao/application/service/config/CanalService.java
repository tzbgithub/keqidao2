package com.qidao.application.service.config;

import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.common.req.BasePageQuery;
import com.qidao.application.model.config.CanalCreateReq;
import com.qidao.application.model.config.CanalRes;
import com.qidao.application.model.config.CanalUpdateReq;
import com.qidao.application.model.config.*;
import com.qidao.framework.service.ServicePage;

import java.util.List;

/**
 * 分发渠道处理业务声明
 *
 * @author liu Le
 * @create 2020-12-29 14:23
 */
public interface CanalService {
    /**
    * 添加各包的分发渠道 <br>
    * @param canalCreateReq {@link CanalCreateReq} - 待添加的渠道对象
    * @return true-添加成功 false-添加失败
    */
    Boolean insert(CanalCreateReq canalCreateReq);

    /**
    * 更新各包的分发渠道 <br>
    * @param canalUpdateReq {@link CanalUpdateReq} - 待更新的渠道对象
    *@return: true-更新成功 false-更新失败
    */
    Boolean update(CanalUpdateReq canalUpdateReq);

    /**
    * 根据ID逻辑删除指定的分发渠道对象 <br>
    * @param baseIdQuery {@link BaseIdQuery} - 待逻辑删除的渠道ID
    * @return  true-删除成功 false-删除失败
    */
    Boolean deleteById(BaseIdQuery baseIdQuery);

    /**
    * 查询所有分发渠道的列表 <br>
    * @param basePageQuery {@link BasePageQuery} - 分页查询对象
    * @return  集合 {@link CanalRes}
    */
    ServicePage<List<CanalRes>> getList(BasePageQuery basePageQuery);

    /**
     * 验证版本是否是最新版本
     */
    CanalRep verificationVersion(CanalReq canalReq);
}
