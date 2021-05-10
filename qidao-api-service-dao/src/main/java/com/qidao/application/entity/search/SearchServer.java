package com.qidao.application.entity.search;

import com.qidao.application.vo.LinkLabelVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchServer {

    private Long serverId;

    private String title;

    private String description;

    private String thumb;

    private String url;

    private LocalDateTime createTime;

    private String province;

    private String city;

    private String area;

    private String money;

    private List<LinkLabelVo> labels;

}
