package com.qidao.application.entity.dynamic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDynamicArticleDo {

    private Long organizationId;

    private Long articleId;

    private boolean verify;

}
