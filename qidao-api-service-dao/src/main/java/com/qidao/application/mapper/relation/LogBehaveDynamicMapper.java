package com.qidao.application.mapper.relation;

import com.qidao.application.entity.relation.LogBehaveDynamic;
import com.qidao.application.entity.relation.LogBehaveDynamicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogBehaveDynamicMapper {
    long countByExample(LogBehaveDynamicExample example);

    int deleteByExample(LogBehaveDynamicExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogBehaveDynamic record);

    int insertSelective(@Param("record") LogBehaveDynamic record, @Param("selective") LogBehaveDynamic.Column ... selective);

    LogBehaveDynamic selectOneByExample(LogBehaveDynamicExample example);

    /**
     * 数据库循环查询LogBehave
     * @param memberId
     * @param dynamicIds
     * @return 集合 {@link LogBehaveDynamic}
     */
    List<Long> getLogBehaveList(@Param("memberId")Long memberId,@Param("dynamicIds") Long[] dynamicIds);

    List<LogBehaveDynamic> selectByExample(LogBehaveDynamicExample example);

    LogBehaveDynamic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogBehaveDynamic record, @Param("example") LogBehaveDynamicExample example, @Param("selective") LogBehaveDynamic.Column ... selective);

    int updateByExample(@Param("record") LogBehaveDynamic record, @Param("example") LogBehaveDynamicExample example);

    int updateByPrimaryKeySelective(@Param("record") LogBehaveDynamic record, @Param("selective") LogBehaveDynamic.Column ... selective);

    int updateByPrimaryKey(LogBehaveDynamic record);

    int batchInsert(@Param("list") List<LogBehaveDynamic> list);

    int batchInsertSelective(@Param("list") List<LogBehaveDynamic> list, @Param("selective") LogBehaveDynamic.Column ... selective);
}