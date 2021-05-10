package com.qidao.application.model.dingtalk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Autuan.Yu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingtalkMsgText {
    private final String msgtype = "text";
    private DingtalkText text;
    private DingtalkAt at;
}
