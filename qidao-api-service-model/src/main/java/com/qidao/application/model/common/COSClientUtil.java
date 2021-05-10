package com.qidao.application.model.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mchange.v2.beans.BeansUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.TransferProgress;
import com.qidao.application.model.common.enums.ConstantEnum;
import com.qidao.application.model.common.oss.CosStsClient;
import com.qidao.application.model.common.oss.OssRep;
import com.qidao.application.model.cos.UploadVideoRes;
import com.qidao.application.model.member.MemberEnum;
import com.qidao.application.model.msg.MsgCacheDO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;
import java.util.TreeMap;
import java.util.concurrent.*;

@Component
@Slf4j
public class COSClientUtil {

    private COSClient cosClient;
    /**
     * 存储通名称     指定要上传到的存储桶
     */
    @Value("${cos.imageBucketName}")
    private String imageBucketName;
    @Value("${cos.videoBucketName}")
    private String videoBucketName;

    private String secretId;
    private String secretKey;
    private String regionName;
    private COSCredentials cred;
    private ClientConfig clientConfig;

    public COSClientUtil(@Value("${cos.secretId}") String secretId, @Value("${cos.secretKey}") String secretKey,
                         @Value("${cos.region}") String regionName) {
        cred = new BasicCOSCredentials(secretId, secretKey);
        clientConfig = new ClientConfig(new Region(regionName));
        cosClient = new COSClient(cred, clientConfig);
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.regionName = regionName;
    }

    /**
     * 销毁
     */
    public void destory() {
        cosClient.shutdown();
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key, String bucketName) {
        /**
         *  设置URL过期时间为10年 3600l* 1000*24*365*10
         */
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 30);
        URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFileCos(InputStream instream, MultipartFile file) {
        String fileName = getFileDir(file);
        String url = null;
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            /**上传文件    filename -> key  指定要上传到 COS 上对象键*/
            cosClient.putObject(imageBucketName, fileName, instream, objectMetadata);
            url = getUrl(fileName, imageBucketName);
            log.info("---- COSClientUtil ----上传腾讯云图片返回地址:{}", url);
        } catch (IOException e) {
            log.info("---- COSClientUtil ----上传图片到腾讯云连接异常----", e);
            fileName = null;
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                log.error("COSClientUtil ----上传图片到腾讯云连接异常--{}--", e);
            }
        }
        return fileName;
    }


    /**
     * 根据路径上传图片
     *
     * @param instream
     * @param filePath
     * @return
     */
    public String uploadFileCos(InputStream instream, String filePath) {
        String url = null;
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(filePath.substring(filePath.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + filePath);
            /**上传文件    filename -> key  指定要上传到 COS 上对象键*/
            cosClient.putObject(imageBucketName, filePath, instream, objectMetadata);
            url = getUrl(filePath, imageBucketName);
            log.info("---- COSClientUtil ----上传腾讯云图片返回地址:{}", url);
        } catch (IOException e) {
            log.info("---- COSClientUtil ----上传图片到腾讯云连接异常----", e);
            filePath = null;
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                log.error("COSClientUtil ----上传图片到腾讯云连接异常--{}--", e);
            }
        }
        return filePath;
    }

    /**
     * 腾讯云上传视频
     */
    public UploadVideoRes uploadVideo(MultipartFile multipartFile) throws IOException {
        TimeInterval timer = DateUtil.timer();
        log.info("uploadVideo -> start -> begin");
        InputStream inputStream = multipartFile.getInputStream();
        try {
            String videoPath = getFileDir(multipartFile);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(videoBucketName, videoPath, inputStream, objectMetadata);

            cosClient.putObject(putObjectRequest);

            // 封面图
            String thumbPath = videoPath.replaceAll("[.][^.]+$", "")+"_0.jpg";

            log.info("uploadVideo -> end -> 用时 -> 毫秒 -> {} videoPath -> {} thumbPath -> {}", timer.interval(),videoPath,thumbPath);
            return UploadVideoRes.builder()
                    .videoPath(videoPath)
                    .thumbPath(thumbPath)
                    .build();
        } catch (CosServiceException e) {
            log.error("---- COSClientUtil ----上传腾讯云视频COS服务异常", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * 截取视频流的图像并上传
     *
     * @return 截取图片的路径
     * @author Autuan.Yu
     */
    @SneakyThrows
    private String grabImageAndUpload(InputStream inputStream, String fileName) {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(inputStream);
        //最后获取到的视频的图片的路径
        String thumbPath = null;
        //Frame对象
        Frame frame = null;
        //标识：取第几帧
        int flag = 0;
        fFmpegFrameGrabber.start();
        //获取视频总帧数
        int ftp = fFmpegFrameGrabber.getLengthInFrames();
        while (flag <= ftp) {
            frame = fFmpegFrameGrabber.grabImage();
				/*
				对视频的第五帧进行处理
				 */
            if (frame != null && flag == 5) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.getBufferedImage(frame);

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                String imagePath = fileName.substring(0, fileName.lastIndexOf("."));

                thumbPath = uploadFileCos(is, imagePath + RandomUtil.randomInt(10000) + ".jpg");
                break;
            }
            flag++;
        }
        fFmpegFrameGrabber.stop();
        fFmpegFrameGrabber.close();
        return thumbPath;
    }

    private ByteArrayOutputStream cloneInputStream(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        return baos;
    }

    /**
     * 按照时间格式进行分夹
     *
     * @param file
     * @return
     */
    public String getFileDir(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String name = time + "/" + RandomUtils.nextInt(0, 10000) + System.currentTimeMillis() + "_" + substring;
        return name;
    }


    /**
     * 格式转换
     *
     * @throws IOException
     */
    public File MultipartFileToFile(MultipartFile file) throws IOException {
        File toFile = null;
        log.info("");
        if ("".equals(file) || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File("/data/apps/temp/" + file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    /**
     * 分段读秒
     *
     * @param transfer
     */
    private void showTransferProgress(Transfer transfer) {
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("--- COSCOlientUtil --休眠2秒失败，请核查", e);
                Thread.currentThread().interrupt();
                return;
            }
            TransferProgress progress = transfer.getProgress();
            long so_far = progress.getBytesTransferred();
            long total = progress.getTotalBytesToTransfer();
            log.info("--- COSCOlientUtil   -- 断点续传进度 ---" + "[{} / {}]\n", so_far, total);
        } while (transfer.isDone() == false);
    }


    /**
     * 获取流文件
     */
    private void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            log.info("----- COSClientUtil ----- 获取流文件失败{}-----", e);
        } finally {
            if (os != null) {
                os.close();
            }
            if (ins != null) {
                ins.close();
            }
        }
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        String type = "";
        if (StringUtils.isNotBlank(filenameExtension)) {
            type = filenameExtension.toLowerCase();
        }
        switch (type) {
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "png":
                return "image/png";
            case "mp4":
                return "image/mp4";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpeg";
            case "html":
                return "text/html";
            case "txt":
                return "text/plain";
            case "vsd":
                return "application/vnd.visio";
            case "pptx":
                return "application/vnd.ms-powerpoint";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "docx":
                return "application/msword";
            case "xml":
                return "text/xml";
            default:
                break;
        }
        return "image/jpeg";
    }


    public  OssRep getToken(Integer type) {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        OssRep ossRep = new OssRep();
        try {
            config.put("SecretId", secretId);
            config.put("SecretKey", secretKey);

            // 临时密钥有效时长，单位是秒，默认1800秒，目前主账号最长2小时（即7200秒），子账号最长36小时（即129600秒）
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            String bucket = null;
            if(type == ConstantEnum.NOT_DEL.getInt()){
                bucket = imageBucketName;
                config.put("bucket", bucket);
            }else {
                bucket = videoBucketName;
                config.put("bucket", bucket);
            }

            // 换成 bucket 所在地区
            config.put("region", regionName);

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子：a.jpg 或者 a/* 或者 * 。
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefix", "*");

            // 密钥的权限列表。简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);
            JSONObject  credential = CosStsClient.getCredential(config);
             Object credentials = credential.get("credentials");
             ossRep = JSONUtil.toBean(JSON.toJSONString(credentials), OssRep.class);
            ossRep.setRegionName(regionName).setBucket(bucket);
        } catch (Exception e) {
            //失败抛出异常
            throw new IllegalArgumentException("no valid secret !");
        }
        return ossRep;
    }

}