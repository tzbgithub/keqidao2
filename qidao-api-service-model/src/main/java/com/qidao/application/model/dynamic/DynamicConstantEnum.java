package com.qidao.application.model.dynamic;

import lombok.Getter;

@Getter
public enum DynamicConstantEnum  {

    /**初始化审核状态 0-待审核**/
    VERIFY_STATUS_INIT(0 , "待审核"),
    /**
     * 3 - 审核通过
     */
    VERIFY_STATUS_PASS(3 , "审核通过"),
    /**当前用户对动态发布者的关注状态，0-已关注**/
    SUBSCRIBE_TRUE(0 , "已关注"),
    /**当前用户对动态发布者的关注状态，1-未关注**/
    SUBSCRIBE_FALSE(1,"未关注"),
    /**当前用户对该动态的收藏状态 0-已收藏**/
    FAVOR_TRUE(0,"已收藏"),
    /**当前用户对该动态的收藏状态 1-未收藏**/
    FAVOR_FALSE(1,"未收藏"),
    /**当前用户对该动态的点赞状态 0-已点赞**/
    LIKE_TRUE(0,"已点赞"),
    /**当前用户对该动态的点赞状态 1-未点赞**/
    LIKE_FALSE(1,"未点赞"),
    /**点赞记录表type 0-动态**/
    LIKE_TYPE_DYNAMIC(0,"动态"),
    /**点赞记录表type 1-评论**/
    LIKE_TYPE_COMMENT(1,"评论"),
    /**动态是否是热门 1-是**/
    HOT_TRUE(1,"热门动态"),
    /**态是否是热门 0-否**/
    HOT_FALSE(0,"非热门动态"),
    /**评论已通过审核**/
    COMMENT_VERIFY_PASS(1 , "已通过审核"),
    /**发布评论审核状态初始化 0- 待审核**/
    COMMENT_VERIFY_INIT(0, "待审核"),
    /** 13 - 发布类型 **/
    SELECT_CONFIG_ARTICLE(13,"发布类型"),
    /**代发布动态**/
    REPLACE_TYPE_DYNAMIC(0 , "代发布动态"),
    /**代发布文章**/
    REPLACE_TYPE_CONTENT(1 , "代发布文章"),
    /**代发布人运营人员**/
    PUBLISH_SALESMAN(0 , "代发布人运营人员"),
    /**代发布人助手**/
    PUBLISH_ASSISTANT(1 , "代发布人助手"),
    ;

    private final Integer code;

    private final String message;

    DynamicConstantEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
