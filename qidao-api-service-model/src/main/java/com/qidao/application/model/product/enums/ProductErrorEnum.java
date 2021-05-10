package com.qidao.application.model.product.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorEnum  implements ResultEnumInterface {
    NOTFOUND_PRODUCT_ERROR("LxAPI-001101", "产品不存在"),
    RERETITION_PRODUCT_ERROR("LxAPI-001202", "产品不可重复");

    private final String code;
    private final String message;

}
