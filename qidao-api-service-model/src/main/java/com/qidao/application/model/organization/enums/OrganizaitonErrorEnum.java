package com.qidao.application.model.organization.enums;

import com.qidao.application.model.common.enums.ResultEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  OrganizaitonErrorEnum implements ResultEnumInterface {

    DELETE_ORGANIZATION_ERROR("LxAPI-000501", "注销失败"),
    INSERT_ORGANIZATION_ERROR("LxAPI-000502", "入驻失败"),
    NOFOUND_ORGANIZATION_ERROR("LxAPI-000503", "组织机构不存在"),
    PARAMS_ORGANIZATION_ERROR("LxAPI-000504", "参数不匹配"),
    SKILL_LONG_ERROR("LxAPI-000505", "您输入的科研方向过多，请适当精简"),
    INDUSTRYID_NO_ERROR("LxAPI-000506", "请选择行业"),
    CHAR_ORGANIZATION_ERROR("LxAPI-000507", "非法字符"),
    REOETITION_ORGANIZATION_ERROR("LxAPI-000508", "该科研人员已注册，请勿重复注册"),
    USERNOTFOUD_ORGANIZATION_ERROR("LxAPI-000509", "用户不存在"),
    SENDTYPE_AHIECEMENTEQUIPMENT_ERROR("LxAPI-000510", "发布人不存在"),
    MESSAGE_AHIECEMENTEQUIPMENT_ERROR("LxAPI-000511", "信息不存在"),
    REQYEST_AHIECEMENTEQUIPMENT_ERROR("LxAPI-000512", "请求无效"),
    CHANGE_ORGANIZATION_ERROR("LxAPI-000513", "变更人不存在"),
    LABELNO_ORGANIZATION_ERROR("LxAPI-000514", "标签不能为空"),
    USERISEXIST_ORGANIZATION_ERROR("LxAPI-000515", "用户已存在"),
    INSERT_ORGANIZATION_NOTEXISE("LxAPI-000516", "已有所属机构"),
    BASE_ORGANIZATION_AUTHENTICATION("LxAPI-000517", "企业已经认证"),
    LABELNO_LONG_ERROR("LxAPI-000518", "单个标签最长16个字符"),
    NOT_IN_CHARGE("LxAPI-000519" , "不是企业"),
    ENTERPRISE_IS_NULL("LxAPI-000520" , "企业内没有其他成员不可退出"),
    APPOINT_ADMIN("LxAPI-000521" , "管理员退出企业需要指派其他成员为管理员"),
    APPOINT_MEMBER_ORGANIZATION("LxAPI-000522" , "您指派的用户不是您企业的员工"),
    ;
    private final String code;
    private final String message;
}
