package com.qidao.application.controller.advertise;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.dto.AdvertiseReq;
import com.qidao.application.service.advertise.AdvertiseService;
import com.qidao.application.vo.AdvertiseCanalChannel;
import com.qidao.application.vo.AdvertisePageRep;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advertise")
@Slf4j
@Api(value = "广告栏位", tags = "广告栏位")
public class AdvertiseController {
    @Autowired
    AdvertiseService advertiseService;

    //    @ApiOperation(value = "广告栏位",notes = "广告栏位")
//    @PostMapping(value = "/findAdvertiseByList")
    @Deprecated
    public ResponseResult<List<AdvertisePageRep>> findAdvertiseByList(@RequestBody AdvertiseReq advertiseReq) {
        List<AdvertisePageRep> advertiseByList = advertiseService.findAdvertiseByList(advertiseReq.getLocation(), advertiseReq.getCancal());
        return Result.ok(advertiseByList);
    }

    @ApiOperation(value = "频道和渠道过滤广告",notes = "频道和渠道过滤广告")
    @PostMapping("/getAdvertiseByCanalAndChannel")
    public ResponseResult<List<AdvertiseCanalChannel>> test(@RequestBody AdvertiseCanalChannel advertiseCanalChannel) {
        List<AdvertiseCanalChannel> advertiseByCanalAndChannel = advertiseService.getAdvertiseByCanalAndChannel(advertiseCanalChannel.getCanalId(), advertiseCanalChannel.getChannelId());
        return Result.ok(advertiseByCanalAndChannel);
    }
}
