package com.qidao.application.mapper.dynamic;

import com.qidao.application.entity.dynamic.Channel;
import com.qidao.application.entity.dynamic.ChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChannelMapper {
    long countByExample(ChannelExample example);

    int deleteByExample(ChannelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Channel record);

    int insertSelective(@Param("record") Channel record, @Param("selective") Channel.Column ... selective);

    Channel selectOneByExample(ChannelExample example);

    List<Channel> selectByExample(ChannelExample example);

    Channel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Channel record, @Param("example") ChannelExample example, @Param("selective") Channel.Column ... selective);

    int updateByExample(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByPrimaryKeySelective(@Param("record") Channel record, @Param("selective") Channel.Column ... selective);

    int updateByPrimaryKey(Channel record);

    int batchInsert(@Param("list") List<Channel> list);

    int batchInsertSelective(@Param("list") List<Channel> list, @Param("selective") Channel.Column ... selective);
}