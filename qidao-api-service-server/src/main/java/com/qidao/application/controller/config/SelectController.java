package com.qidao.application.controller.config;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.config.DynamicIndustryRes;
import com.qidao.application.model.config.SelectConfigType;
import com.qidao.application.model.config.SelectConfigVo;
import com.qidao.application.model.config.SelectGetByTypeReq;
import com.qidao.application.model.label.ListLabelReq;
import com.qidao.application.model.mp.label.ListLabelRes;
import com.qidao.application.service.config.SelectConfigService;
import com.qidao.application.service.label.LabelService;
import com.qidao.framework.service.ServicePage;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "下拉选择接口")
@RestController
@RequestMapping("/config")
@Slf4j
public class SelectController {

    @Autowired
    private SelectConfigService selectService;

    @Autowired
    private LabelService labelService;

    /**
     * 根据类型分页查询
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据类型分页查询", notes = "类型（type）选择：<br><span>&emsp;&emsp;</span>0-服务需求领域;<br><span>&emsp;&emsp;</span>1-组织规模大小;<br><span>&emsp;&emsp;</span>2-研发规模大小;<br><span>&emsp;&emsp;</span>3-需求研发经费;<br><span>&emsp;&emsp;</span>4-学历;<br><span>&emsp;&emsp;</span>5-职位;<br><span>&emsp;&emsp;</span>6-行业;<br><span>&emsp;&emsp;</span>7-投诉;<br><span>&emsp;&emsp;</span>8-反馈;<br><span>&emsp;&emsp;</span>9-会员领域;<br><span>&emsp;&emsp;</span>10-职称;<br><span>&emsp;&emsp;</span>11-企业技术服务;<br><span>&emsp;&emsp;</span>12-个人技术服务;<br><span>&emsp;&emsp;</span>13-成果文章类型;<br><span>&emsp;&emsp;</span>14-技术成熟度;<br><span>&emsp;&emsp;</span>15-合作类型;<br><span>&emsp;&emsp;</span>16-热门搜索;<br><span>&emsp;&emsp;</span>17-帮助管理;<br><span>&emsp;&emsp;</span>18-配置管理;<br><span>&emsp;&emsp;</span>19-广告位置")
    @PostMapping("/getSelectByType")
    public ResponseResult<ServicePage<List<SelectConfigVo>>> getSelectByType(@RequestBody @Validated SelectGetByTypeReq req) {
        log.info("SelectController -> getSelectByType -> start -> req:{} ", req);
        ServicePage<List<SelectConfigVo>> selectVos = selectService.getSelectByType(req);
        log.info("SelectController -> getSelectByType -> end");
        return Result.ok(selectVos);
    }

    @ApiOperation("根据父ID一级级查询")
    @PostMapping("/getSelectByPid")
    public ResponseResult<List<SelectConfigVo>> getSelectByPid(@RequestBody @Validated BaseIdQuery pid) {
        log.info("SelectController -> getSelectByPid -> start -> pid:{}", pid);
        List<SelectConfigVo> selectVos = selectService.getSelectByPid(pid.getId());
        log.info("SelectController -> getSelectByPid -> end");
        return Result.ok(selectVos);
    }

    @ApiOperation("根据type直接返回所有父子选择")
    @PostMapping("/getSelectFatherSonByType")
    public ResponseResult<List<SelectConfigVo>> getSelectFatherSonByType(@RequestBody @Validated SelectConfigType type) {
        log.info("SelectController -> getSelectFatherSonByType -> start -> type:{}", type);
        List<SelectConfigVo> selectVos = selectService.getSelectFatherSonByType(type.getType());
        log.info("SelectController -> getSelectFatherSonByType -> end");
        return Result.ok(selectVos);
    }

    @ApiOperation(value = "内容标签查询 (发布成果 选择内容标签)", notes = "查询组织机构标签")
    @PostMapping("/listAchievementLabel")
    ResponseResult<List<ListLabelRes>> listAchievementLabel(@RequestBody @Validated ListLabelReq req) {
        log.info("SelectController -> listAchievementLabel -> start -> req -> {}", req);
        List<ListLabelRes> data = labelService.listAchievementLabel(req);
        log.info("SelectController -> listAchievementLabel -> end");
        return Result.ok(data);
    }

    @ApiOperation("返回首页行业筛选以及热门行业")
    @PostMapping("/getDynamicIndustry")
    ResponseResult<DynamicIndustryRes> getDynamicIndustry() {
        log.info("SelectController -> getDynamicIndustry -> start ");
        DynamicIndustryRes data = selectService.getDynamicIndustry();
        log.info("SelectController -> getDynamicIndustry -> end");
        return Result.ok(data);
    }
}
