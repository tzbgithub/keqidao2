package com.qidao.application.entity.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerPageDo {

    private List<Long> labels;

    private List<Long> industryIds;

    private Long specAreaId;

    private Long memberId;

}
