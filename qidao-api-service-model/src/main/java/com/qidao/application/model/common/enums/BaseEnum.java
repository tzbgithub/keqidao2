package com.qidao.application.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Autuan.Yu
 * 全局状态返回码枚举
 */

@Getter
@AllArgsConstructor
public enum BaseEnum implements ResultEnumInterface{

    /**
     * 基础定义
     */

    /**请求成功*/
    SUCCESS("Sx200", "请求成功"),
    /**请求失败*/
    FAIL("LxAPI-CCCCCC","请求失败"),

    /**
     * 版本错误
     */
    VERSION_ERROR("T-API-000002","版本号不正确"),

    /**
     * 超时/繁忙/不支持
     */

    /**请求超时*/
    TIME_OUT("T-API-000000","连接服务时超过服务器给定时间"),
    /**不支持的请求方式*/
    NOT_SUPPORT("T-API-000001","不支持的请求方式"),


    /**
     * 业务类错误
     */

    /**业务逻辑错误**/
    BUSINESS_LOGIC_ERROR("LxAPI-CCCCCC", "业务逻辑错误"),

    /**
     * 参数类错误
     */

    /**存在必填项未填写错误*/
    VERIFY("Px000-000001","存在必填项未填写错误"),
    /***JSON/XML解析错误**/
    JSON_XML_FORMAT_ERROR("Px000-000002", "JSON/XML解析错误"),
    /***数值范围溢出错误**/
    NUM_SCOPE_ERROR("Px000-000003", "数值范围溢出错误"),
    /***索引越界错误**/
    INDEX_CROSS_ERROR("Px000-000004", "索引越界错误"),
    /***多个同名参数错误**/
    BASE_NAME_PARAM_ERROR("Px000-000005", "多个同名参数错误"),
    /***字符串长度过长/过短错误**/
    STRING_LENGTH_LONG_ERROR("Px000-000006", "字符串长度过长/过短错误"),
    /***字符串内容不符合规则错误**/
    STRING_REMARK_ERROR("Px000-000007", "字符串内容不符合规则错误"),
    /***缺少参数错误**/
    LACK_PARAM_ERROR("Px000-000008", "缺少参数错误"),
    /***文件类型不合法错误**/
    FILE_TYPE_ERROR("Px000-000009", "文件类型不合法错误"),
    /***文件名错误**/
    FILE_NAME_ERROR("Px000-000010", "文件名错误"),
    /***文件大小超限错误**/
    FILE_SIZE_LONG_ERROR("Px000-000011", "文件大小超限错误"),
    /***文件媒体类型错误**/
    FILE_VIDEO_TYPE_ERROR("Px000-000012", "文件媒体类型错误"),
    /***图片尺寸错误**/
    IMAGE_SIZE_ERROR("Px000-000013", "图片尺寸错误"),
    /***图片格式错误**/
    IMAGE_FORMAT_ERROR("Px000-000014", "图片格式错误"),
    /***视频格式错误**/
    VIDEO_FORMAT_ERROR("Px000-000015", "视频格式错误"),
    /***语音格式错误**/
    VOICE_FORMAT_ERROR("Px000-000016", "语音格式错误"),
    /***文件格式转换错误**/
    FILE_FORMAT_CONVERT_ERROR("Px000-000017", "文件格式转换错误"),
    /***系统配置信息错误**/
    SYSTEM_PROFILE_ERROR("Px000-000018", "系统配置信息错误"),
    /**URL过长错误*/
    URL_LONG_ERROR("Px000-000019", "URL过长错误"),


    /**
     * 服务类错误
     * /

     /**数据库无法连接错误*/
    DB_COON_ERROR("Hx000-000001", "数据库无法连接错误"),
    /**连接中断错误*/
    COON_BREAK_ERROR("Hx000-000002", "连接中断错误"),
    /**磁盘满错误*/
    DISK_FULL_ERROR("Hx000-000003", "磁盘满错误"),
    /**网络不稳定错误*/
    NET_UNSTABLE_ERROR("Hx000-000004", "网络不稳定错误"),
    /**无可用服务错误*/
    UN_SERVICE_ERROR("Hx000-000005", "无可用服务错误"),
    /**服务调用方权限不足错误*/
    SERVICE_ROOT_ERROR("Hx000-000006", "服务调用方权限不足错误"),
    /**数据库内部错误*/
    DB_INTERNAL_ERROR("Hx000-000007", "数据库内部错误"),
    /**基础服务通信错误*/
    BASE_SERVICE_COON_ERROR("Hx000-000008", "基础服务通信错误"),
    /**IO错误*/
    IO_ERROR("Hx000-000009", "IO错误"),


    /**
     * 安全类错误
     */

    /**账户/密码验证失败错误*/
    ACCOUNT_VERIFY_PASSWORD_ERROR("Dx000-000001", "账户/密码验证失败错误"),
    /**验证码错误/过期*/
    VERIFY_CODE_ERROR("Dx000-000002", "验证码错误/过期"),
    /**未登录错误*/
    UN_LOGIN_ERROR("Dx000-000003", "未登录错误"),
    /**权限不足错误*/
    UN_ROOT_ERROR("Dx000-000004", "权限不足错误"),
    /**令牌失效错误*/
    TOKEN_FAIL("Dx000-000005","令牌失效错误"),
    /**无证书或证书过期错误*/
    TOKEN_EXPIRED("Dx000-000006", "无证书或证书过期错误"),
    /**账户被禁用/冻结错误*/
    ACC_STOP_ERROR("Dx000-000007", "账户被禁用/冻结错误"),
    /**密码过期错误*/
    PWD_EXP_ERROR("Dx000-000008", "密码过期错误"),
    /**弱口令错误*/
    WEAK_TOKEN_ERROR("Dx000-000009", "弱口令错误"),
    /**接口调用次数限制错误*/
    FREQUENT("Dx000-000010", "接口调用过于频繁"),
    /**USB key检测错误*/
    USB_KEY_ERROR("Dx000-000011", "USB key检测错误"),
    /**密码修改错误*/
    PWD_UP_ERROR("Dx000-000012", "密码修改错误"),
    /**验证码输入错误*/
    SMS_CODE_ERROR("Dx000-000013", "验证码输入错误"),
    /*** 登录方式有误*/
    LOGIN_TYPE_ERROR("Dx000-0000014", "登录方式有误"),
    ;
    private String code;
    private String message;
}
