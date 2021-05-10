package com.qidao.application.model.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductPermissionConstantEnum {
    /**
     *购买权限 0-全都可以买
     */
    PERMISSION_ALL(0),
    /**
     *购买权限 1-实验室可以买
     */
    PERMISSION_LABORATORY(1),
    /**
     *购买权限 2-企业可以买
     */
    PERMISSION_ENTERPRISE(2),
    ;

    private final Integer code;

    public static ProductPermissionConstantEnum valueOf(Integer code){
        for(ProductPermissionConstantEnum constantEnum : ProductPermissionConstantEnum.values()) {
            if(constantEnum.code.equals(code)) {
                return constantEnum;
            }
        }
        return ProductPermissionConstantEnum.PERMISSION_ALL;
    }
}
