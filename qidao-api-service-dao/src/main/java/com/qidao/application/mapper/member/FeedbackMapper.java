package com.qidao.application.mapper.member;

import com.qidao.application.entity.member.Feedback;
import com.qidao.application.entity.member.FeedbackExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedbackMapper {

    List<Feedback> selectAll();

    long countByExample(FeedbackExample example);

    int deleteByExample(FeedbackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Feedback record);

    int insertSelective(@Param("record") Feedback record, @Param("selective") Feedback.Column ... selective);

    Feedback selectOneByExample(FeedbackExample example);

    List<Feedback> selectByExample(FeedbackExample example);

    Feedback selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Feedback record, @Param("example") FeedbackExample example, @Param("selective") Feedback.Column ... selective);

    int updateByExample(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    int updateByPrimaryKeySelective(@Param("record") Feedback record, @Param("selective") Feedback.Column ... selective);

    int updateByPrimaryKey(Feedback record);

    int batchInsert(@Param("list") List<Feedback> list);

    int batchInsertSelective(@Param("list") List<Feedback> list, @Param("selective") Feedback.Column ... selective);
}