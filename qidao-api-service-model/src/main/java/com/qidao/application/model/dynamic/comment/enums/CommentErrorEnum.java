package com.qidao.application.model.dynamic.comment.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorEnum implements ResultEnumInterface {

    COMMENT_DELETED("LxAPI-014001","评论已删除"),
    NOT_AGREE("LxAPI-014002" , "您没有点赞"),
    AGREE("LxAPI-014003" , "您已经点过赞")
    ;

    private final String code;

    private final String message;

}
