package com.qidao.application.mapper.dynamic;

import com.qidao.application.entity.dynamic.Complaint;
import com.qidao.application.entity.dynamic.ComplaintExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ComplaintMapper {
    long countByExample(ComplaintExample example);

    int deleteByExample(ComplaintExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Complaint record);

    int insertSelective(@Param("record") Complaint record, @Param("selective") Complaint.Column ... selective);

    Complaint selectOneByExample(ComplaintExample example);

    List<Complaint> selectByExample(ComplaintExample example);

    Complaint selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Complaint record, @Param("example") ComplaintExample example, @Param("selective") Complaint.Column ... selective);

    int updateByExample(@Param("record") Complaint record, @Param("example") ComplaintExample example);

    int updateByPrimaryKeySelective(@Param("record") Complaint record, @Param("selective") Complaint.Column ... selective);

    int updateByPrimaryKey(Complaint record);

    int batchInsert(@Param("list") List<Complaint> list);

    int batchInsertSelective(@Param("list") List<Complaint> list, @Param("selective") Complaint.Column ... selective);
}