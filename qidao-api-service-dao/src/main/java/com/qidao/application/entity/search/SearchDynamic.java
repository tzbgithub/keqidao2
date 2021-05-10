package com.qidao.application.entity.search;

import com.qidao.application.entity.label.Label;
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
public class SearchDynamic {

    private Long dynamicId;

    private String headImage;

    private Long publisherId;

    private LocalDateTime publishTime;

    private String title;

    private String url;

    private String video;

    private String thumb;

    private Integer commentNum;

    private Integer type;

    private String summary;

    private Integer likeNum;

    private String img;

    private String content;

    private String education;

    private String cooperation;

    private String name;

    private String belong;

    private String organizationName;

    private String position;

    private String labelIds;

    private String labelVals;
}
