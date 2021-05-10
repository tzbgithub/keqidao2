package com.qidao.application.model.dynamic.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEventParam {
    /**
     * 用户ID
     */
    private Long memberId;
}
