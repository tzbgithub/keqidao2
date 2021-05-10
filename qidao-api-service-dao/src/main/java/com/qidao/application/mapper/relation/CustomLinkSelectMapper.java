package com.qidao.application.mapper.relation;


import com.qidao.application.vo.SelectConfigResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CustomLinkSelectMapper {
   public List<SelectConfigResp> findSelectConfigByOrganization(Long organizationId);
    List<Long> listMemberByPositionAndTime(int howMany, List<Long> blockList);

    List<Long> listMemberWithoutSubscribe(Long memberId);
}