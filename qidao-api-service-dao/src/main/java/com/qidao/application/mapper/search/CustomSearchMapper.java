package com.qidao.application.mapper.search;

import com.qidao.application.entity.search.SearchDynamic;
import com.qidao.application.entity.search.SearchMember;
import com.qidao.application.entity.search.SearchServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomSearchMapper {
    /**
     * 搜索动态
     * @param keyword 关键字
     * @param memberIds 会员ID 集合
     * @return {@link SearchDynamic}
     */
    List<SearchDynamic> getSearchDynamic(String keyword , List<Long> memberIds);

//    List<SearchServer> getSearchServer(String keyword);

    List<SearchMember> getSearchMember(String keyword , List<Long> memberIds);

    List<SearchDynamic> getSearchAchievementEquipment(String keyword , List<Long> memberIds);

}
