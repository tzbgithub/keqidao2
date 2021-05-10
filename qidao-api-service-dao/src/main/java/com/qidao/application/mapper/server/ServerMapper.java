package com.qidao.application.mapper.server;

import com.qidao.application.entity.server.Server;
import com.qidao.application.entity.server.ServerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerMapper {
    long countByExample(ServerExample example);

    int deleteByExample(ServerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Server record);

    int insertSelective(@Param("record") Server record, @Param("selective") Server.Column ... selective);

    Server selectOneByExample(ServerExample example);

    List<Server> selectByExample(ServerExample example);

    Server selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Server record, @Param("example") ServerExample example, @Param("selective") Server.Column ... selective);

    int updateByExample(@Param("record") Server record, @Param("example") ServerExample example);

    int updateByPrimaryKeySelective(@Param("record") Server record, @Param("selective") Server.Column ... selective);

    int updateByPrimaryKey(Server record);

    int batchInsert(@Param("list") List<Server> list);

    int batchInsertSelective(@Param("list") List<Server> list, @Param("selective") Server.Column ... selective);
}