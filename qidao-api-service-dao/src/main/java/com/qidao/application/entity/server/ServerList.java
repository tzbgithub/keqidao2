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
public class ServerList {

    private Long id;

    private Long memberId;

    private LocalDateTime createTime;

    private Integer status;

    private String title;

    private String addressProvinceName;

    private String addressCityName;

    private String addressAreaName;

    private String description;

    private Long specAmountId;

    private LocalDateTime solutionTime;

    private Long industryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerList that = (ServerList) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
