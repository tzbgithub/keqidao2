package com.qidao.application.service.config;

import com.qidao.application.model.config.DynamicIndustryRes;
import com.qidao.application.model.config.SelectConfigVo;
import com.qidao.application.model.config.SelectGetByTypeReq;
import com.qidao.framework.service.ServicePage;

import java.util.List;

public interface SelectConfigService {

    /**
     * 根据类型分页查询
     * @param req 请求对象 {@link SelectGetByTypeReq}
     * @return 分页集合 {@link SelectConfigVo}
     */
    ServicePage<List<SelectConfigVo>> getSelectByType(SelectGetByTypeReq req);

    /**
     * 根据父ID查询
     * @param pid 父ID
     * @return 集合 {@link SelectConfigVo}
     */
    List<SelectConfigVo> getSelectByPid(Long pid);

    /**
     * 根据type查询父子结构
     * @param type 类型
     * @return 集合 {@link SelectConfigVo}
     */
    List<SelectConfigVo> getSelectFatherSonByType(Integer type);

    /**
     * 获取动态筛选行业
     * @return 动态筛选行业 {@link DynamicIndustryRes}
     */
    DynamicIndustryRes getDynamicIndustry();
}