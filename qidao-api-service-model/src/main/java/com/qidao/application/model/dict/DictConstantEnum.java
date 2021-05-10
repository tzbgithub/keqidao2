package com.qidao.application.model.dict;

import lombok.Getter;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 10:42 AM
 */
@Getter
public enum DictConstantEnum {
    /**
     * dict字典表
     */
    SERVER_AREA(0 , "服务需求领域"),
    ORGANIZATION_SCALE(1 , "组织规模大小"),
    DEVELOP_SCALE(2,"研发规模大小"),
    DEVELOP_FUND(3,"研发经费"),
    DEGREE(4,"学历"),
    POSITION(5,"职位"),
    INDUSTRY(6,"行业"),
    COMPLAINT(7,"投诉原因"),
    FEEDBACK(8,"反馈理由"),
    MEMBER_AREA(9,"会员领域"),
    POSITION_TITLE(10,"职称"),
    ENTERPRISE_DEVELOP_SERVER(11 , "企业技术服务"),
    PERSONAL_DEVELOP_SERVER(12, "个人技术服务"),
    TECHNOLOGY_MATURITY(14, "技术成熟度"),
    COOPERATION_TYPE(15, "合作类型"),
    HOT_SEARCH(16, "热门搜索"),
    HELP(17, "帮助管理"),
    CONFIGURE(18, "配置管理"),
    ADVERTISE_LOCATION(19, "广告位置")
    ;

    private final Integer id;

    private final String name;

    DictConstantEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
