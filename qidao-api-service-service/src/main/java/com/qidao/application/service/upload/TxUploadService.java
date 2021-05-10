package com.qidao.application.service.upload;

import com.qidao.application.model.common.oss.OssRep;
import com.qidao.application.model.cos.UploadVideoRes;
import com.qidao.framework.web.ResponseResult;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface TxUploadService {

    /**
     * 腾讯云上传图片
     */
    public String uploadImage(MultipartFile file);


    /**
     * 腾讯云上传视频
     * @param file
     * @return
     * @throws IOException
     */
    public UploadVideoRes uploadVideo(MultipartFile file) throws  IOException;


    OssRep getTengToken(int type);
}
