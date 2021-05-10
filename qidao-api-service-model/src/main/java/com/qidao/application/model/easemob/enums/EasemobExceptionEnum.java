package com.qidao.application.model.easemob.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EasemobExceptionEnum implements ResultEnumInterface {
    /*
    用户体系
     */
    USER("LxAPI-070000" , "用户不存在"),
    REPEAT("LxAPI-070001" , "用户重复注册"),
    FRIEND("LxAPI-070002" , "您不能添加此好友"),

    /*
    文件上传下载
     */
    SIZE("LxAPI-071000" , "请上传10MB以内的图片"),
    IMG("LxAPI-071001" , "请上传一张图片"),

    /*
    OTHER
     */
    SELF("LxAPI-079000" , "不能对自己进行此操作"),
    ;


    private final String code;
    private final String message;

}
