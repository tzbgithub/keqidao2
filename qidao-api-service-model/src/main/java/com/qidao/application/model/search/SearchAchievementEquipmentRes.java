package com.qidao.application.model.search;

import com.qidao.application.model.dynamic.DynamicLabelRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opencv.android.LoaderCallbackInterface;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAchievementEquipmentRes {

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "用户名称")
    private String memberName;

    @ApiModelProperty(value = "用户头像")
    private String memberHeadImage;

    @ApiModelProperty(value = "用户组织机构名称")
    private String organizationName;

    @ApiModelProperty(value = "所属单位")
    private String belong;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "成果ID")
    private Long achievementEquipmentId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "成熟度")
    private String maturity;

    @ApiModelProperty(value = "封面")
    private String thumb;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "链接")
    private String url;

    @ApiModelProperty(value = "图片")
    private String imgs;

    @ApiModelProperty(value = "标签")
    private List<DynamicLabelRes> labelVos;

}
