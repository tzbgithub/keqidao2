package com.qidao.application.entity.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipMsg {
    private Long id;

    private String title;

    private String content;

    private Integer status;

    private String sendTime;
}
