package com.qidao.application.entity.dynamic;

import com.qidao.application.vo.LinkLabelVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.ldap.PagedResultsControl;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyAgree {

    private String headImage;

    private Long PublisherId;

    private String belong;

    private String organizationName;

    private String name;

    private String position;

    private String education;

    private Long dynamicId;

    private LocalDateTime publishTime;

    private String title;

    private String url;

    private String video;

    private Integer commentNum;

    private Integer likeNum;

    private String dynamicContent;

    private String img;

    private String thumb;

    private String summary;

    private List<LinkLabelVo> labels;

    private String cooperationType;

    private Integer type;

    private String labelIds;

    private String labelVals;

}
