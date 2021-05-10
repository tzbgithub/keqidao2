package com.qidao.application.mapper.config;

import com.qidao.application.entity.config.SelectConfig;
import com.qidao.application.entity.config.SelectConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomSelectConfigMapper {
    List<SelectConfig> getSelectConfigByIds(@Param("delFlag") Byte delFlag, @Param("ids") Long[] ids);

    /**
     * 获取指定类型type、指定数量topN的热门对象的val值(字符串)
     */
    List<String> getHotSelectConfig(@Param("type") Integer type, @Param("topN") Integer topN,@Param("status") Integer status,@Param("delFlag") Byte delFlag);

    List<Long> getIndustryId();
}