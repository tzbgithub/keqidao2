package com.qidao.application.controller.easemob;

import com.qidao.application.model.common.Result;
import com.qidao.application.model.easemob.chatfile.UploadRes;
import com.qidao.application.model.easemob.enums.EasemobExceptionEnum;
import com.qidao.application.service.im.EasemobChatFilesService;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "环信IM-文件集成")
@Slf4j
@RestController
@RequestMapping("/easemob/api/file")
public class EasemobAPIChatFilesController {
    @Autowired
    private EasemobChatFilesService easemobChatFilesService;

    @PostMapping("/upload")
    @ApiOperation(value = "环信上传图片", notes = "@author 于先知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "要上传的图片文件：1张；<=10Mb")
    })
    ResponseResult<UploadRes> upload(@RequestParam(value = "file") MultipartFile[] file) {
        // 参数校验
        if (file.length != 1) {
            log.info("EasemobAPIChatFilesController -> upload -> file.length != 1 -> 请上传一张图片");
            return Result.fail(EasemobExceptionEnum.IMG);
        }

        MultipartFile uploadFile = file[0];
        if (null == uploadFile || uploadFile.isEmpty()) {
            log.info("EasemobAPIChatFilesController -> upload -> 请上传图片");
            return Result.fail(EasemobExceptionEnum.IMG);
        }
        long ableSize10Mb = 10 * 1024 * 1024L;
        if (uploadFile.getSize() > ableSize10Mb) {
            log.info("EasemobAPIChatFilesController -> upload -> 请上传10MB以内的图片");
            return Result.fail(EasemobExceptionEnum.SIZE);
        }

        UploadRes obj = easemobChatFilesService.uploadChatFiles(uploadFile);
        return Result.ok(obj);
    }
}
