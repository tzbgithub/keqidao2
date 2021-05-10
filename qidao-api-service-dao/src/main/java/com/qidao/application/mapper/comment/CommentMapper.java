package com.qidao.application.mapper.comment;

import com.qidao.application.entity.comment.Comment;
import com.qidao.application.entity.comment.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(@Param("record") Comment record, @Param("selective") Comment.Column ... selective);

    Comment selectOneByExample(CommentExample example);

    Comment selectOneByExampleWithBLOBs(CommentExample example);

    List<Comment> selectByExampleWithBLOBs(CommentExample example);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example, @Param("selective") Comment.Column ... selective);

    int updateByExampleWithBLOBs(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(@Param("record") Comment record, @Param("selective") Comment.Column ... selective);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    int batchInsert(@Param("list") List<Comment> list);

    int batchInsertSelective(@Param("list") List<Comment> list, @Param("selective") Comment.Column ... selective);
}