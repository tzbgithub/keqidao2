package com.qidao.application.model.common;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import com.qidao.application.model.config.properties.TokenConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Token 加解密工具
 * @author Autuan.Yu
 */
@Component
public class QiDaoEncodeUtil {
    /**
     * REFRESH TOKEN 密钥
     */
    private QiDaoEncodeUtil(@Autowired TokenConfigDTO tokenConfigDTO){
        this.des = SecureUtil.des(tokenConfigDTO.getKey());
    }
    /**
     * DES 加密工具类 用于token
     */
    private  DES des;

    /**
     * DES 加密方法
     * @param source 要加密的字符串
     * @return 加密后的字符串
     */
    public  String desEncode(String source){
        return des.encryptHex(source);
    }

    /**
     * DES 解密方法
     * @param data 要解密的数据
     * @return 解密后的方法
     */
    public  String desDecrypt(String data){
        // todo exception 处理 cn.hutool.crypto.CryptoException: IllegalBlockSizeException: last block incomplete in decryption
        // todo (Autuan)[21.3.31]
        return des.decryptStr(data);
    }
}
