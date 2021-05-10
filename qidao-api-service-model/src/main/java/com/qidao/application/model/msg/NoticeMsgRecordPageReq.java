package com.qidao.application.model.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("通知消息记录查询请求对象")
public class NoticeMsgRecordPageReq extends BasePageQuery {

    @ApiModelProperty(value = "模糊搜索关键字", example = "关键字", hidden = true)
    private String keyword;

    @ApiModelProperty(value = "时间范围（开始） 格式： yyyy-MM-dd HH:mm:ss", example = "2021-01-01 09:00:00", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeStart;

    @ApiModelProperty(value = "时间范围（结束） 格式： yyyy-MM-dd HH:mm:ss", example = "2021-01-01 18:00:00", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime queryTimeEnd;


    @ApiModelProperty(name = "memberId", value = "会员ID", example = "134134370467842", required = true)
    @NotNull(message = "会员id不能为空")
    private Long memberId;

    @ApiModelProperty(name = "statusList", value = "消息记录状态列表", notes = "状态 0-（发送成功）未读 1-（发送成功）已读 2-未发送状态  3-发送成功 4-发送失败", example = "[0,1]", hidden = true)
    private List<Integer> statusList;

    @ApiModelProperty(name = "menuId", value = "菜单类型ID", example = "134134370467842", hidden = true)
    private Long menuId;
}
