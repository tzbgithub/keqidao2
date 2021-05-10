package com.qidao.application.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMsgRecordPageReqVo {

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 消息记录状态
     */
    private List<Integer> statusList;

    /**
     * 菜单类型ID
     */
    private Long menuId;
}
