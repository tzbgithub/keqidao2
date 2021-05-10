package com.qidao.application.model.organization.enums;

import lombok.Getter;

@Getter
public enum AchievementEquipmentEnum {

    /**   全部废弃
     标签类别：0-动态；1-服务；2-频道；3-用户；4-组织机构 5-成果文章
     */

    ACH_DYNAMIC("ACH_DYNAMIC",0,"动态"),
    ACH_SERVICE("ACH_SERVICE",1,"服务"),

    ACH_CHANNEL("ACH_CHANNEL",2,"频道"),
    ACH_MEMBER("ACH_MEMBER",3,"用户"),
    ACH_ORIGANIZATION("ACH_ORIGANIZATION",4,"组织机构"),
    ACH_PATENT("ACH_PATENT",5,"成果文章");


    private final String  key;
    private final Integer value;
    private final String describe;
    private AchievementEquipmentEnum(String key, Integer value, String describe){
        this.key = key;
        this.value = value;
        this.describe = describe;
    }
    //根据key获取枚举
    public static AchievementEquipmentEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(AchievementEquipmentEnum temp:AchievementEquipmentEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }


}
