package com.reai.toolutil.baseUtil;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * @author 86136
 */
public class JasyptUtils {

    public static void main(String[] args) {
        String info = encrypt("==cMovz4XRaW1mQaSnRzz9nZOTmNdS8");
        String jm = decrypt("djSqOUTmY5IBzbjvoR3B5kMT2QEyHOREoF5/QsYLE6jRea2nD5IKSmkrDiiVc3rBFwRNAtDZQiM=");
        System.out.println(info);
        System.out.println(jm);
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return
     */
    public static String encrypt(String plaintext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        // 指定算法
        config.setAlgorithm("PBEWithMD5AndDES");
        // 指定秘钥，和yml配置文件中保持一致
        config.setPassword("haha");
        encryptor.setConfig(config);
        // 生成加密数据
        return encryptor.encrypt(plaintext);
    }

    /**
     * 解密
     *
     * @param data 加密后数据
     * @return
     */
    public static String decrypt(String data) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("haha");
        encryptor.setConfig(config);
        // 解密数据
        return encryptor.decrypt(data);
    }
}
