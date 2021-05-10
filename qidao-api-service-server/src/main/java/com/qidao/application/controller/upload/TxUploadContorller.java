package com.qidao.application.controller.upload;
import com.qidao.application.model.common.Result;
import com.qidao.application.model.common.oss.OssRep;
import com.qidao.application.model.common.req.BaseIdQuery;
import com.qidao.application.model.cos.UploadVideoRes;
import com.qidao.application.model.server.ServerErrorEnum;
import com.qidao.application.service.upload.impl.TxUploadServiceImpl;
import com.qidao.framework.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@Api(value = "腾讯云上传", tags = "腾讯云上传")
@Slf4j
@RequestMapping("/upload")
public class TxUploadContorller {
    @Autowired
    TxUploadServiceImpl txUploadService;

    @ApiOperation(value = "腾讯云上传图片", notes = "腾讯云上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "images", value = "图片", required = true, allowMultiple = true, paramType = "query", dataType = "file", dataTypeClass = MultipartFile[].class)
    })
    @PostMapping(value = "/sendTxImage")
    public ResponseResult<List<String>> uploadPicture(@RequestParam(value = "images") MultipartFile[] multipartFiles) throws IOException {
        log.info("--- TxUploadContorller ---> uploadPicture ---> : 多个文件multipartFiles={},个数{}", multipartFiles, multipartFiles.length);
        if (multipartFiles.length <= 0) {
            return Result.fail(ServerErrorEnum.IMAGENO_UPLOAD_ERROR);
        }
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            log.info("--- TxUploadContorller ---> uploadPicture ---> : 单个文件multipartFile={}", multipartFile);
            String image = txUploadService.uploadImage(multipartFile);
            images.add(image);
        }

        return Result.ok(images);
    }

    @ApiOperation(value="腾讯云上传视频", notes="腾讯云上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "video", value = "图片", required = true,allowMultiple = true,paramType = "query",dataType = "file",dataTypeClass = MultipartFile[].class)
    })

    @PostMapping(value = "/sendTxVideo")
    public ResponseResult<UploadVideoRes> uploadVideo(@RequestParam(value = "video") MultipartFile[] video) throws IOException {
        if(video.length<=0){
            return Result.fail(ServerErrorEnum.VIDEONO_UPLOAD_ERROR);
        }
        log.info("--- TxUploadContorller ---> uploadVedio ---> : multipartFile={}",video);
        UploadVideoRes uploadVideoRes = txUploadService.uploadVideo(video[0]);
        return  Result.ok(uploadVideoRes);

    }

    @PostMapping(value = "/addProFile", headers = "content-type=multipart/form-data")
    @ApiOperation(value="腾讯云批量上传图片", notes="腾讯云批量上传图片")
    @ResponseBody
    public ResponseResult<List<String>> uploadFile(MultipartRequest request){
        Map<String, MultipartFile> fileMap = request.getFileMap();
        log.info("--- TxUploadContorller ---> addProFile ---> : 图片数={}",fileMap.size());
        ArrayList<String> images = new ArrayList<>();
        for(String key:fileMap.keySet()){
            MultipartFile value = fileMap.get(key);//
            log.info("--- TxUploadContorller ---> addProFile ---> : value={}",value);
            String image = txUploadService.uploadImage(value);
            images.add(image);
        }
        return Result.ok(images);
    }
    @PostMapping(value = "/getTengToken")
    @ApiOperation(value="获取上传临时token", notes="获取上传临时token   0代表图片上传的token 其他为视频上传的token")
    public ResponseResult<OssRep> getTengToken(@RequestBody  @Validated BaseIdQuery baseIdQuery) {
        OssRep tengToken = txUploadService.getTengToken(Integer.parseInt(String.valueOf(baseIdQuery.getId())));
        return Result.ok(tengToken);
    }

}
