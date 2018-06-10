package top.kwseeker.www.ssm_island.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 3DES对称加解密
 * 加密算法：
 *      DES/CBC/NoPadding (56)
 *      DES/CBC/PKCS5Padding (56)
 *      DES/ECB/NoPadding (56)
 *      DES/ECB/PKCS5Padding (56)
 *
 */
public class DES3Util {

    private static final String ALGORITHM_DESEDE = "DESede";
    private static final String CHARSET = "UTF-8";
    // 加密密钥
    private static final byte[] keyByte = "3desKeyResetUserPassword".getBytes();    // 24bytes

    // 加密
    public static String encryptCode(String str) throws Exception {
        byte[] encryptData = encryptCode(str.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptData);
    }
    public static byte[] encryptCode(byte[] data) throws Exception {
        return encryptOrDecrypt(data, keyByte, Cipher.ENCRYPT_MODE);
    }

    // 解密
    public static String decryptCode(String str) throws Exception {
        byte[] encryptData = Base64.getDecoder().decode(str);
        return new String(decryptCode(encryptData), CHARSET);
    }
    public static byte[] decryptCode(byte[] data) throws Exception {
        return encryptOrDecrypt(data, keyByte, Cipher.DECRYPT_MODE);
    }

    public static byte[] encryptOrDecrypt(byte[] data, byte[] key, int mode) throws Exception {
        // 指定算法
        Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
        SecretKey key3DES = new SecretKeySpec(key, ALGORITHM_DESEDE);
        // 用密钥初始化Cipher对象
        cipher.init(mode, key3DES);
        return cipher.doFinal(data);
    }
}
