package com.qidao.application.model.config.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QiDaoSinceErrorEnum implements ResultEnumInterface {
    CANAL("LxAPI-000101", "此渠道尚未开放访问"),
    VERSION("LxAPI-000102", "请更新版本"),
    VERSION_END("LxAPI-000103", "此功能已停止维护"),
    VERSION_FORMAT("LxAPI-000104", "版本号格式不正确"),
    ;
    private final String code;
    private final String message;

}
