package com.qidao.application.controller.wx;

import com.qidao.application.model.common.Result;
import com.qidao.application.service.official.WxService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "微信相关接口")
@RestController
@RequestMapping("/mp/wxService")
@Slf4j
public class WxController {

    @Autowired
    private WxService wxService;

    @ApiOperation("微信自动登录返回unionId")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "登录时获取的 code", dataType = "String", dataTypeClass = String.class, required = true)})
    @GetMapping("/getUnionId")
    public ResponseResult<String> getUnionId(@RequestParam(name = "code") String code) {
        log.info("WxController -> getUnionId -> start -> code:{}", code);
        String unionId = wxService.getUnionId(code);
        log.info("WxController -> getUnionId -> end");
        return Result.ok(unionId);
    }
}
