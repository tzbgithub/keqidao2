package com.qidao.application.model.member.subscribe;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2020/12/15 2:09 PM
 */
@Getter
public enum SubscribeEnum{
    /**
     * DELETE_FLAG_ 逻辑删除: 0 否(默认)， 1 是
     *
     * TYPE_ 行为类别：0 关注，1 屏蔽
     *
     * SUBSCRIBE_TYPE_ 关注对象身份：0 会员，1 组织机构 ，2 仅实验室
     */
    DELETE_FLAG_NO(0),
    DELETE_FLAG_YES(1),
    FLAG_FOLLOW(0),
    FLAG_BLOCK(1),
    TYPE_FOLLOW(0),
    TYPE_BLOCK(1),
    SUBSCRIBE_TYPE_MEMBER(0),
    SUBSCRIBE_TYPE_ORGANIZATION(1),
    SUBSCRIBE_TYPE_LABORATORY(2)
    ;



    private final int value;

    SubscribeEnum(int value) {
        this.value = value;
    }

    public static SubscribeEnum getSubscribeType(Integer typeInt){
        switch (typeInt){
            case 0:
                return SubscribeEnum.SUBSCRIBE_TYPE_MEMBER;
            case 1:
                return SubscribeEnum.SUBSCRIBE_TYPE_ORGANIZATION;
            case 2:
                return SubscribeEnum.SUBSCRIBE_TYPE_LABORATORY;
            default:
                return null;
        }
    }

}
