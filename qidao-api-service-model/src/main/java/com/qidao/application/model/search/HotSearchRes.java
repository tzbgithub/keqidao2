package com.qidao.application.model.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/3/2 10:16 AM
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "HotSearchRes",description = "[响应]热门搜索词汇/标签")
public class HotSearchRes {

    @ApiModelProperty(value = "词汇/标签")
    private List<String> values;

}
