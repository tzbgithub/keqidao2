package com.qidao.application.entity.dynamic;

import com.qidao.application.entity.label.Label;
import com.qidao.application.entity.relation.LinkSelect;
import com.qidao.application.vo.LinkLabelVo;
import com.qidao.application.vo.SelectConfigResp;
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
public class DynamicDetailed {

    private String technologyMaturity;

    private String cooperationType;

    private String headImage;

    private Long PublisherId;

    private String belong;

    private String organizationName;

    private String name;

    private String position;

    private String education;

    private Integer Status;

    private Long dynamicId;

    private LocalDateTime pushTime;

    private String title;

    private String url;

    private String video;
    private String thumb;

    private Integer commentNum;

    private Integer likeNum;

    private String content;

    private String img;

    private Integer needVip;

    private Integer type;

    private String labelIds;

    private String labelVals;

    private String selectConfigIds;

    private String article;

}
