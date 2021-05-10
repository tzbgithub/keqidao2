package com.qidao.application.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BecomeVipEventParam {
    private Long memberId;
    private LocalDateTime vipStartTime;
    private LocalDateTime vipEndTime;
}
