package com.qidao.application.entity.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerPage {

    private Long id;

    private Long memberId;

    private LocalDateTime createTime;

    private Integer status;

    private String title;

    private String addressProvinceName;

    private String addressCityName;

    private String addressAreaName;

    private String description;

    private String specAmountName;

    private LocalDateTime solutionTime;

    private String labels;

    private Long industryId;
}
