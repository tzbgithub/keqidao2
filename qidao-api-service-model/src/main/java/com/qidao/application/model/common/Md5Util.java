package com.qidao.application.model.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

@Slf4j
public class Md5Util {

    /**
     *
     * @param param  加密串
     * @return  salt
     */
    public static String getSalt(String param) {
        String salt =null;

        try {
            String encrypt = getEncrypt(param); // NOSONAR
            salt = encrypt.substring(0, 8);// NOSONAR
        } catch (Exception e) {
            log.info("md5 获取salt异常", e);
        }

        return  salt;
    }

    /**
     * 获取密文
     */
    public  static  String getEncrypt(String param){
        String crypt = null;
        try {
            crypt = DigestUtils.md5DigestAsHex(param.getBytes());
            return crypt;
        } catch (Exception e) {
            log.debug("md5 加密返回异常", e);
        }
        return crypt;
    }

    public  static  String getCrypt(String param){
        String crypt = null;
        try {
            String salt = getSalt(param);// NOSONAR
            crypt = DigestUtils.md5DigestAsHex((param + salt).getBytes());
            return crypt;
        } catch (Exception e) {
            log.debug("md5 加密返回异常", e);
        }
        return crypt;
    }

}
