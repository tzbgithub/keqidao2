package com.qidao.application.model.contract;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractBaseInfoRes {
    private Long id;
    private Long organizationIdPartyA;

    private Long organizationIdPartyB;

    private Long memberIdPartyA;

    private Long memberIdPartB;

    private String no;

    private LocalDateTime signTime;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer status;

    private String serverTitle;
}
