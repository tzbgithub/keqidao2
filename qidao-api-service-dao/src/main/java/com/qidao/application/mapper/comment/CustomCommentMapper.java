package com.qidao.application.mapper.comment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomCommentMapper {

    int checkCommentPublisher(Long memberId , Long commentId);

    int checkDynamicPublisher(Long memberId , Long commentId);

}
