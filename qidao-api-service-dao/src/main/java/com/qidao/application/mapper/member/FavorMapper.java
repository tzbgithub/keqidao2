package com.qidao.application.mapper.member;

import com.qidao.application.entity.member.Favor;
import com.qidao.application.entity.member.FavorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavorMapper {
    long countByExample(FavorExample example);

    int deleteByExample(FavorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Favor record);

    int insertSelective(@Param("record") Favor record, @Param("selective") Favor.Column ... selective);

    Favor selectOneByExample(FavorExample example);

    /**
     * 循环遍历查询取结果集
     * @param memberId  用户ID
     * @param dynamicIds  动态ID数组
     * @return list集合 {@link Favor}
     */
    List<Long> getExampleList(@Param("memberId")Long memberId,@Param("dynamicIds") Long[] dynamicIds);

    List<Favor> selectByExample(FavorExample example);

    Favor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Favor record, @Param("example") FavorExample example, @Param("selective") Favor.Column ... selective);

    int updateByExample(@Param("record") Favor record, @Param("example") FavorExample example);

    int updateByPrimaryKeySelective(@Param("record") Favor record, @Param("selective") Favor.Column ... selective);

    int updateByPrimaryKey(Favor record);

    int batchInsert(@Param("list") List<Favor> list);

    int batchInsertSelective(@Param("list") List<Favor> list, @Param("selective") Favor.Column ... selective);
}