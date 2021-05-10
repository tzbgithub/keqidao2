package com.qidao.application.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementEquipmentDto {
    @NotNull(message = "请拉取对应组织机构")
    @ApiModelProperty(name = "memberId", value = "组织标识  前端抓取标识）", required = true,example = "234234234234234")
    private Long memberId;
    @NotNull(message = "请选择对应发布选项")
    @ApiModelProperty(name = "type", value = "成果类型", required = true,example = "[1,2,3]")
    private List<Long> type;
    @Size(max = 40,message = "标题最多40字")
    @NotNull(message = "请输入标题")
    @ApiModelProperty(name = "title", value = "标题", required = true,example = "企岛科技")
    private String title;
    @ApiModelProperty(name = "video", value = "视频", required = false,example = "视频链接url")
    private String video;
    @ApiModelProperty(name = "imgs", value = "图片，以`,`(英文逗号)分割", required = false,example = "['xxx.jpg','xxx.jpg,xxx.jpg']")
    private List<String> imgs;
    @ApiModelProperty(name = "content", value = "内容", required = true,example = "内容")
    @Size(max = 400,message = "内容最多400字")
    @NotNull(message = "请输入内容")
    private String content;
    @ApiModelProperty(name = "labelId", value = "标签标识", required = false,example = "[1,2]")
    private Long[] labelId;
    @ApiModelProperty(name = "thumb", value = "缩略图", required = false,example = "xxx.jpg")
    private String thumb;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "maturity", value = "成熟度", required = false,example = "1")
    private Long maturity;
    @ApiModelProperty(name = "url", value = "链接", required = false,example = "https://www.teambition.com/project/5fd6fb829689204566094f08/tasks/scrum/5fd6fb821941e50044d74e60")
    private String url;
}
