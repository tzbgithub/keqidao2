package com.qidao.application.service.upload.impl;

import com.qidao.application.model.common.COSClientUtil;
import com.qidao.application.model.common.oss.OssRep;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.cos.UploadVideoRes;
import com.qidao.application.model.server.ServerErrorEnum;
import com.qidao.application.service.upload.TxUploadService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Slf4j

@Service
public class TxUploadServiceImpl implements TxUploadService {
    @Autowired
    private COSClientUtil cosClientUtil;
    @Autowired
    TxUploadService txUploadService;
    @Override
    public String uploadImage(MultipartFile file) {
        if (file.getSize() > 10 * 1024 * 1024) {
            log.info("---- TxUploadServiceImpl  --- uploadImage --上传图片大小不能超过10M----");
            throw new BusinessException(ServerErrorEnum.SIZE_UPLOAD_ERROR);
        }

        String url = null;
        try {
            InputStream inputStream = file.getInputStream();
            url = cosClientUtil.uploadFileCos(inputStream, file);
            log.info("---- TxUploadServiceImpl  --- uploadImage --文件名为null:  {}----",url);
            if(url==null){
                throw new BusinessException(ServerErrorEnum.FAIL_UPLOAD_ERROR);
            }
            return url;
        } catch (Exception e) {
            log.error("---- TxUploadServiceImpl  --- uploadImage --腾讯云上传失败----",e);
        }
        return"腾讯云上传失败";
    }




    @Override
    public UploadVideoRes uploadVideo(MultipartFile file)   throws  IOException{
        try {
            log.info("TxUploadServiceImpl -> uploadVideo -> start");
            UploadVideoRes uploadVideoRes = cosClientUtil.uploadVideo(file);
            if(uploadVideoRes==null){
                throw new BusinessException(ServerErrorEnum.FAIL_UPLOAD_ERROR);
            }
            return uploadVideoRes;
        } catch (Exception e) {
            log.error("---- TxUploadServiceImpl  --- uploadVedio --腾讯云上传视频失败----",e);
        }
       return  null;
    }

    @Override
    public OssRep getTengToken(int type) {
        OssRep token = cosClientUtil.getToken(type);
        return  token;
    }


}
