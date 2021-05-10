package com.qidao.application.mapper.member;

import com.qidao.application.entity.member.Subscribe;
import com.qidao.application.entity.member.SubscribeExample;
import java.util.List;

import com.qidao.application.entity.relation.LogBehaveDynamic;
import org.apache.ibatis.annotations.Param;

public interface SubscribeMapper {
    long countByExample(SubscribeExample example);

    int deleteByExample(SubscribeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Subscribe record);

    int insertSelective(@Param("record") Subscribe record, @Param("selective") Subscribe.Column ... selective);

    Subscribe selectOneByExample(SubscribeExample example);

    /**
     * 避免循环体操作数据库
     * @param memberId
     * @param subscribeIds
     * @return
     */
    List<Long> selectSubscribeId(@Param("memberId")Long memberId,@Param("subscribeIds") Long[] subscribeIds);

    List<Subscribe> selectByExample(SubscribeExample example);

    Subscribe selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Subscribe record, @Param("example") SubscribeExample example, @Param("selective") Subscribe.Column ... selective);

    int updateByExample(@Param("record") Subscribe record, @Param("example") SubscribeExample example);

    int updateByPrimaryKeySelective(@Param("record") Subscribe record, @Param("selective") Subscribe.Column ... selective);

    int updateByPrimaryKey(Subscribe record);

    int batchInsert(@Param("list") List<Subscribe> list);

    int batchInsertSelective(@Param("list") List<Subscribe> list, @Param("selective") Subscribe.Column ... selective);
}