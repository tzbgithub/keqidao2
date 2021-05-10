package com.qidao.application.entity.dynamic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDynamicArticleDo {

    private Long articleId;

    private Long sourceId;

    private boolean verify;

}
