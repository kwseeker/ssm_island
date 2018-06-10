package top.kwseeker.www.ssm_island.util;

import java.security.MessageDigest;

/**
 * MD5+SALT加密
 * salt通过对username进行处理后获得
 */
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String misorderSaltOrigin(String saltOrigin) {
        // ...
        return saltOrigin;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }
    private static String byteToHexString(byte b) {
        int n = b & 0xff;
        int d1 = n >>> 4 & 0xf; // 前面补零
        int d2 = n & 0xf;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回大写的加密密文
     * @param origin
     * @param charsetName
     * @return
     */
    private static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);  // 为了不更改原字符串对象新建对象
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
                byte[] mdDigest = md.digest(resultString.getBytes(charsetName));
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return resultString.toUpperCase();
    }

    public static String MD5AddSaltEncodeUTF8(String saltOrigin, String source) {
        String sourceAddSalt = misorderSaltOrigin(saltOrigin) + source;
        return MD5Encode(sourceAddSalt, "utf-8");
    }

}
