package com.qidao.application.service.im;

import com.qidao.application.model.easemob.chatfile.UploadRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * todo 注释补全 (Autuan)[start.21.3.15]
 * 文件上传下载
 */
public interface EasemobChatFilesService {
    /**
     * 上传图片 <br>
     * 注意：上传文件大小不能超过 10M，超过会上传失败。
     * @param file 图片
     * @return {@link UploadRes}
     */
    UploadRes uploadChatFiles(MultipartFile file);

    /**
     * 下载语音/图片文件
     * @return
     */
    Object downloadChatFiles();

    /**
     * 下载缩略图
     * @return
     */
    Object downloadThumb();
}
