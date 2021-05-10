package com.qidao.application.model.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qidao.application.model.common.req.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/6 2:25 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ServerListReq", description = "[请求]获取需求列表")
public class ServerListReq extends BasePageQuery implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户Id不能为空")
    @ApiModelProperty(value = "用户Id")
    private Long memberId;
    @ApiModelProperty(name = "specAreaId", value = "需求领域id (null即不作为查询条件)", example = "1")
    private Long specAreaId;
    @ApiModelProperty(name = "industryId", value = "推荐本行业", example = "[1]")
    private List<Long> industryId;
    @ApiModelProperty(name = "type", value = "0 最新  1 推荐 industryId必须传 ", example = "1",required = false)
    private Integer type;
    @ApiModelProperty(name = "labelIdArray", value = "标签id数组 (null即不作为查询条件)", example = "[1,3]")
    private Long[] labelIdArray;
}
