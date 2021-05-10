package com.qidao.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMemberVo {
    private Long id;
    private Integer level;
    private Byte delFlag;

}
