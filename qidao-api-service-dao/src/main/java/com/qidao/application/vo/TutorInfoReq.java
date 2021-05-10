package com.qidao.application.vo;

import lombok.Data;

/**
 * 导师头像
 */
@Data
public class TutorInfoReq {
    private  Long id;
    private  String headImage;
    private  String name;
    private  String positionId;
    private  String val;
}
