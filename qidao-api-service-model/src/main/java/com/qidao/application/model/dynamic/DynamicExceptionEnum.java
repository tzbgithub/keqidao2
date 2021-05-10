package com.qidao.application.model.dynamic;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum DynamicExceptionEnum implements ResultEnumInterface {

    /**只有实验室可以发布动态**/
    ONLY_LABORATORY("LxAPI-010001" , "只有实验室可以发布动态"),
    /**视频和图片只能选择一项添加**/
    VIDEO_OR_IMG("LxAPI-010002" , "视频和图片只能选择一项添加"),
    /**带有视频或图片必须添加封面**/
    VIDEO_OR_IMG_THUMB("LxAPI-010003" , "带有视频或图片必须添加封面"),
    /**纯文字内容不需要添加封面**/
    CONTENT_NOT_THUMB("LxAPI-010004" , "纯文字内容不需要添加封面"),
    /**没有点过赞无法取消**/
    DYNAMIC_NOT_LIKE("LxAPI-010005" , "没有点过赞无法取消"),
    /**点过赞不可重复点赞**/
    DYNAMIC_AFTER_LIKE("LxAPI-010006" , "点过赞不可重复点赞"),
    /**
     * 只能评论一次
     */
    AFTER_COMMENT("Lx-API-020006", "只能评论一次"),
    /**该动态已删除**/
    DELETE_TRUE("LxAPI-010007" , "该动态已删除"),
    /**
     * 用户不存在
     */
    NOT_EXIST("LxAPI-010008" , "用户不存在"),
    /**
     * 您不是该动态的发布者没有权限删除该动态
     */
    DONT_DELETE("LxAPI-010009" , "您不是该动态的发布者没有权限删除该动态"),
    /**
     *动态仅允许当前登录用户点赞
     */
    NOT_USER_AGREE("LxAPI-010010","仅允许当前登录用户对动态点赞")
    ;

    private final String code;
    private final String message;

    DynamicExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
