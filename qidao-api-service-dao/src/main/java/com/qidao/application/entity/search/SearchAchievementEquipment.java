package com.qidao.application.entity.search;

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
public class SearchAchievementEquipment {

    private Long memberId;

    private String memberName;

    private String memberHeadImage;

    private String organizationName;

    private String belong;

    private String education;

    private LocalDateTime createTime;

    private String position;

    private Long achievementEquipmentId;

    private String title;

    private String summary;

    private String maturity;

    private String thumb;

    private String video;

    private String url;

    private String imgs;

    private List<LinkLabelVo> labelVos;

}
