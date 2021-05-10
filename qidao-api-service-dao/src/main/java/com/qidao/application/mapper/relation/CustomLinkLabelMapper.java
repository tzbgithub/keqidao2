package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkLabel;
import com.qidao.application.entity.relation.LinkLabelExample;
import com.qidao.application.vo.LinkLabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CustomLinkLabelMapper {
    public  List<LinkLabelVo> findLinkLabelBySourceId(Long sourceId);
}