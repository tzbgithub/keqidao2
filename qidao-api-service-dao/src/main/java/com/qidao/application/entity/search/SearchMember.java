package com.qidao.application.entity.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMember {

    private Long id;

    private String name;

    private String headImage;

    private String organizationName;

    private String belong;

    private String education;

    private String position;

}
