package com.qidao.application.model.dingtalk;

import lombok.Getter;

/**
 * @author Autuan.Yu
 */
@Getter
public class DingtalkText {
    private String content;

    public DingtalkText(String content){
        this.content= content + "\n 烦请各位尽快处理";
    }

}
