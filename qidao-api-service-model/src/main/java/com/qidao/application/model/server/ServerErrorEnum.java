package com.qidao.application.model.server;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerErrorEnum implements ResultEnumInterface {
    UNLAWFUL_SERVER_ERROR("LxAPI-000901", "非法操作"),
    VIP_SERVER_ERROR("LxAPI-000902", "您还不是会员"),
    SENDPER_NOTFOUND_ERROR("LxAPI-000903", "发布人不存在"),
    TARGET_NOTFOUND_ERROR("LxAPI-000904", "目标不存在"),
    UNDERTAKING_SERVER_ERROR("LxAPI-000905", "服务已经被承接"),
    IMAGE_UPLOAD_ERROR("LxAPI-000906", "图片上传有误"),
    VIDEO_UPLOAD_ERROR("LxAPI-000907", "视频上传有误"),
    IMAGENO_UPLOAD_ERROR("LxAPI-000908", "请上传图片"),
    VIDEONO_UPLOAD_ERROR("LxAPI-000909", "请上传视频"),
    SIZE_UPLOAD_ERROR("LxAPI-000910", "上传图片大小不能超过10M"),
    FAIL_UPLOAD_ERROR("LxAPI-000911", "上传失败"),
    IMAGE_SIZE_ERROR("LxAPI-000912", "最多上传九张图片"),
    IMAGENOTHUB_UPLOAD_ERROR("LxAPI-000913", "请上传视频封面"),

    ;

    private final String code;
    private final String message;
}
