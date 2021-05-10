package com.qidao.application.model.dingtalk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingtalkAt {
    private List<String> atMobiles;
    private boolean isAtAll;
}
