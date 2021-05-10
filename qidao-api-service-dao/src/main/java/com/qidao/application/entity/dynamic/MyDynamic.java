package com.qidao.application.entity.dynamic;

import com.qidao.application.entity.member.AssistantInfo;
import com.qidao.application.vo.LinkLabelVo;
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
public class MyDynamic {

    private Long dynamicId;

    private Long publisherId;

    private LocalDateTime publishTime;

    private String title;

    private String url;

    private String video;

    private String thumb;

    private String dynamicContent;

    private String headImage;

    private Integer commentNum;

    private Integer verifyStatus;

    private String summary;

    private Integer likeNum;

    private String img;

    private String content;

    private String name;

    private String belong;

    private String organizationName;

    private String position;

    private String education;

    private List<LinkLabelVo> labels;

    private String cooperationType;

    private Integer type;

    private String labelIds;

    private String labelVals;

    private AssistantInfo assistant;
}
