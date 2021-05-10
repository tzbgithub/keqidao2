package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LinkDynamicChannel;
import com.qidao.application.entity.relation.LinkDynamicChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LinkDynamicChannelMapper {
    long countByExample(LinkDynamicChannelExample example);

    int deleteByExample(LinkDynamicChannelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkDynamicChannel record);

    int insertSelective(@Param("record") LinkDynamicChannel record, @Param("selective") LinkDynamicChannel.Column ... selective);

    LinkDynamicChannel selectOneByExample(LinkDynamicChannelExample example);

    List<LinkDynamicChannel> selectByExample(LinkDynamicChannelExample example);

    LinkDynamicChannel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkDynamicChannel record, @Param("example") LinkDynamicChannelExample example, @Param("selective") LinkDynamicChannel.Column ... selective);

    int updateByExample(@Param("record") LinkDynamicChannel record, @Param("example") LinkDynamicChannelExample example);

    int updateByPrimaryKeySelective(@Param("record") LinkDynamicChannel record, @Param("selective") LinkDynamicChannel.Column ... selective);

    int updateByPrimaryKey(LinkDynamicChannel record);

    int batchInsert(@Param("list") List<LinkDynamicChannel> list);

    int batchInsertSelective(@Param("list") List<LinkDynamicChannel> list, @Param("selective") LinkDynamicChannel.Column ... selective);
}