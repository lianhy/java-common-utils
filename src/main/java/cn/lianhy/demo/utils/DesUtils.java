package cn.lianhy.demo.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 3DES加密解密
 *
 * key要求24位
 */
public class DesUtils {

    public static final String DES_NAME = "DESede";
    public static final String ALGORITHM = "DESede/ECB/PKCS5Padding";
    public static final String CHAR_ENCODING = "UTF-8";

    public static String encrypt(String data,String key){
        try {
            return encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String decrypt(String data,String key){
        try {
            return decrypt(Base64.decodeBase64(data), key.getBytes(CHAR_ENCODING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encrypt(byte[] data, byte[] key) {
        try{
            DESedeKeySpec deSedeKeySpec= new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
            SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptValue = cipher.doFinal(data);
            return Base64.encodeBase64String(encryptValue);
        }catch (Exception e){
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String decrypt(byte[] data, byte[] key) {
        try{
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
            SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptValue = cipher.doFinal(data);
            return new String(decryptValue, CHAR_ENCODING);
        }catch (Exception e){
            throw new RuntimeException("decrypt fail!", e);
        }
    }
}
