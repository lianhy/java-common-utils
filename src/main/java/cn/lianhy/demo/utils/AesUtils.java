package cn.lianhy.demo.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 1.AES加密模式：ECB/CBC/CTR/OFB/CFB
 * 2.填充：pkcs5padding/pkcs7padding/zeropadding/iso10126/ansix923
 * 3.数据块：128位/192位/256位
 * 4.KEY 密码
 * 5.IV 偏移量
 * 6.输出 base64/hex
 * 7.字符集：gb2312/gbk/gb18030/utf8
 */
public class AesUtils {
    private static final String AES_NAME = "AES";
    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String CHAR_ENCODING = "UTF-8";

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key,byte[] iv) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
        return result;
    }

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key,byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static String encrypt(String data, String key,String iv) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING),iv.getBytes(CHAR_ENCODING));
            return Base64.encodeBase64String(valueByte);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static String decrypt(String data, String key,String iv) {
        try {
            byte[] valueByte = decrypt(Base64.decodeBase64(data), key.getBytes(CHAR_ENCODING),iv.getBytes(CHAR_ENCODING));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

}
