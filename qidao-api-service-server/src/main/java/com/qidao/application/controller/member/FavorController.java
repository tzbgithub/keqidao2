package com.qidao.application.controller.member;

import com.qidao.application.config.aop.OperLog;
import com.qidao.application.config.aop.QiDaoPermission;
import com.qidao.application.config.enums.BusinessType;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.member.favor.FavorDeleteReq;
import com.qidao.application.model.member.favor.FavorInsertReq;
import com.qidao.application.service.member.FavorService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "会员收藏")
@RestController
@RequestMapping("member/favor")
@Slf4j
public class FavorController {

    @Autowired
    private FavorService favorService;

    /**
     * 新增收藏
     * @param req
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "新增收藏", dataType = "FavorInsertReq", dataTypeClass = FavorInsertReq.class, required = true)})
    @ApiOperation("新增收藏")
    @PostMapping("/createFavor")
    @OperLog(title = "新增收藏", businessType = BusinessType.INSERT,isAsyncSaveToDB = true)
    @QiDaoPermission
    public ResponseResult<Boolean> create(@RequestBody @Validated FavorInsertReq req){
        log.info("FavorController -> create -> start -> favor:{}", req);
        Boolean isCreated = favorService.create(req);
        log.info("FavorController -> create -> end");
        return Result.ok(isCreated);
    }

    /**
     * 删除收藏
     * @param req
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "动态id对象", dataType = "FavorDeleteReq", dataTypeClass = FavorDeleteReq.class, required = true)})
    @ApiOperation(value = "删除收藏")
    @DeleteMapping("/delete")
    @OperLog(title = "删除收藏", businessType = BusinessType.DELETE,isAsyncSaveToDB = true)
    @QiDaoPermission
    public ResponseResult<Object> delete(@RequestBody FavorDeleteReq req) {
        log.info("FavorController -> delete -> start -> dynamicId:{}", req);
        favorService.deleteByDynamicId(req);
        log.info("FavorController -> delete -> end");
        return Result.ok();
    }

    /**
     * 清空收藏
     * @param req
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "清空收藏请求参数", dataType = "BaseIdQuery", dataTypeClass = BaseIdQuery.class, required = true, example = "666")})
    @ApiOperation(value = "清空收藏")
    @DeleteMapping("/empty")
    @OperLog(title = "清空收藏", businessType = BusinessType.DELETE, isAsyncSaveToDB = true)
    public ResponseResult<Object> empty(@RequestBody BaseIdQuery req) {
        log.info("FavorController -> delete -> start -> param:{}", req);
        favorService.empty(req.getId());
        log.info("FavorController -> delete -> end");
        return Result.ok(true);
    }

}
