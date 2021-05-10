package com.qidao.application.model.label;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.Getter;

@Getter
public enum LinkLabelEnum{

    /**
     * link_label表中的type
     * 0--动态
     * 1--服务（需求）
     * 2--频道
     * 3--用户
     * 4--组织机构
     * 5--成果文章
     * **/
    LINK_LABEL_DYNAMIC(0 , "动态"),
    LINK_LABEL_SERVER(1 , "服务"),
    LINK_LABEL_CHANNEL(2 , "频道"),
    LINK_LABEL_MEMBER(3 , "用户"),
    LINK_LABEL_ORGANIZATION(4 , "组织机构"),
    LINK_LABEL_ACHIEVEMENT(5 , "成果文章")
    ;

    private final Integer code;

    private final String message;

    LinkLabelEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
