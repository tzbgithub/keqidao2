package com.qidao.application.mapper.msg;

import com.qidao.application.entity.msg.Msg;
import com.qidao.application.entity.msg.MsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgMapper {
    long countByExample(MsgExample example);

    int deleteByExample(MsgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Msg record);

    int insertSelective(@Param("record") Msg record, @Param("selective") Msg.Column ... selective);

    Msg selectOneByExample(MsgExample example);

    Msg selectOneByExampleWithBLOBs(MsgExample example);

    List<Msg> selectByExampleWithBLOBs(MsgExample example);

    List<Msg> selectByExample(MsgExample example);

    Msg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Msg record, @Param("example") MsgExample example, @Param("selective") Msg.Column ... selective);

    int updateByExampleWithBLOBs(@Param("record") Msg record, @Param("example") MsgExample example);

    int updateByExample(@Param("record") Msg record, @Param("example") MsgExample example);

    int updateByPrimaryKeySelective(@Param("record") Msg record, @Param("selective") Msg.Column ... selective);

    int updateByPrimaryKeyWithBLOBs(Msg record);

    int updateByPrimaryKey(Msg record);

    int batchInsert(@Param("list") List<Msg> list);

    int batchInsertSelective(@Param("list") List<Msg> list, @Param("selective") Msg.Column ... selective);
}