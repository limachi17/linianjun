package com.moxian.common.login.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * <ul>
 * <li>BASE64的加密解密是双向的，可以求反解。</li>
 * <li>MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。</li>
 * <li>HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。</li>
 * <li>DES DES-Data Encryption Standard,即数据加密算法。
 * DES算法的入口参数有三个:Key、Data、Mode。
 * <ul>
 * <li>Key:8个字节共64位,是DES算法的工作密钥;</li>
 * <li>Data:8个字节64位,是要被加密或被解密的数据;</li>
 * <li>Mode:DES的工作方式,有两种:加密或解密。</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Ice_Liu
 * 
 */
public class HMacMD5 {
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA";
    /**
     * MAC算法可选以下多种算法
     * 
     * <pre>
     *  
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512 
     * </pre>
*/
    public static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64解密
     * 
     * @param key
     * @return byte[]
     * @throws Exception
*/
    public static byte[] decryptBASE64(String key) throws Exception {
	    return Base64.decodeBase64(key);
    }

    /**
     * BASE64 加密
     * 
     * @param key
     * @return String
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);
    }

    /**
     * MD5加密
     * 
     * @param data
     * @return byte[]
     * @throws Exception
*/
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA加密
     * 
     * @param data
     * @return byte[]
     * @throws Exception
*/
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     * 
     * @return String
     * @throws Exception
*/
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC 加密
     * 
     * @param data 被加密的原文
     * @param key  加密密钥
     * @return byte[]
     * @throws Exception
*/
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * DES 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     * 
     * <pre>
     * DES                  key size must be equal to 56 
     * DESede(TripleDES)    key size must be equal to 112 or 168 
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
     * RC2                  key size must be between 40 and 1024 bits 
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
     * </pre>
     */
    public static final String ALGORITHM = "DES";

    /**
     * DES 算法转换密钥<br>
     * 
     * @param key
     * @return Key
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = null;
        if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            secretKey = keyFactory.generateSecret(dks);
        } else {
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
            secretKey = new SecretKeySpec(key, ALGORITHM);
        }
        return secretKey;
    }

    /**
     * DES 算法解密
     * 
     * @param data
     * @param key
     * @return byte[]
     * @throws Exception
*/
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * DES 算法加密
     * 
     * @param data
     * @param key
     * @return byte[]
     * @throws Exception
*/
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * DES 算法生成密钥
     * 
     * @return String
     * @throws Exception
*/
    public static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * DES 算法生成密钥
     * 
     * @param seed
     * @return String
     * @throws Exception
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;
        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }
    
    /**
     * 用seed的MD5做密钥
     * 
     */
    public static String md5Key(String seed) throws Exception {
        String md5OfKey;
        if (seed != null) {
            md5OfKey = ByteUtils.bytesToHexString(encryptMD5(seed.getBytes()));
        } else {
            md5OfKey = ByteUtils.bytesToHexString(encryptMD5("".getBytes()));
        }
        return md5OfKey;
    }
    
    /**
     * 
     * @param data
     * @param key
     * @return String
     */
    
    public static String encrypt(String data, String key){
        String sEncrypt = "";
        try {
            byte[] bData = data.getBytes("utf-8");
            byte[] bEncrypt = encrypt(bData, md5Key(key));
            sEncrypt = ByteUtils.bytesToHexString(bEncrypt);
            System.out.println(key);
            System.out.println(sEncrypt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return sEncrypt;
    }
    /**
     * 
     * @param data
     * @param key
     * @return String
     */
    
    public static String decrypt(String data, String key){
        String sDecrypt = "";
        try {
            byte[] base64Decrypt = ByteUtils.hexStringToBytes(data);
            byte[] bDecrypt = decrypt(base64Decrypt, md5Key(key));
            sDecrypt = new String(bDecrypt, "utf-8");
        } catch(Exception e){
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return sDecrypt;
    }

    
    
    public static void main(String[] args) {
        try {
            String s = "阿伯才的覆盖";
            String b = HMacMD5.encryptBASE64(s.getBytes("UTF-8"));
            System.out.println("BASE64加密后:" + b);
            byte[] c = HMacMD5.decryptBASE64(b);
            System.out.println("BASE64解密后:" + new String(c, "UTF-8"));

            c = encryptMD5(s.getBytes());
            System.out.println("MD5   加密后:" + new BigInteger(c).toString(16));

            c = encryptSHA(s.getBytes());
            System.out.println("SHA   加密后:" + new BigInteger(c).toString(16));

            String key = initMacKey();
            System.out.println("HMAC密匙:" + key);
            c = encryptHMAC(s.getBytes(), key);
            System.out.println("HMAC  加密后:" + new BigInteger(c).toString(16));

            key = initKey();
            System.out.println(ALGORITHM + "密钥:\t" + key);
            c = encrypt(s.getBytes("UTF-8"), key);
            System.out.println(ALGORITHM + "   加密后:" + new BigInteger(c).toString(16));
            c = decrypt(c, key);
            System.out.println(ALGORITHM + "   解密后:" + new String(c, "UTF-8"));

            System.out.println("key of hello: " + md5Key("hello"));
            System.out.println("key of world: " + md5Key("world"));
            
            s = "hello";
            System.out.println("明文:" + s);
            byte[] ccc = encrypt(s.getBytes(Charset.forName("utf-8")), md5Key("Hello"));

            System.out.println("DES加密后:" +  encryptBASE64(ccc));
            ccc = decrypt(ccc, md5Key("Hello"));
            System.out.println("DES解密后:" +  new String(ccc, "utf-8"));

            String eeee = encrypt("hello", "hello");
            String dece = decrypt(eeee, "hello");
            System.out.println("encrypt: " + eeee);
            System.out.println("decrypt: " + dece);
            
            System.out.println("aaaa" + encrypt("a45e40a60093475458859d3b0a06497d", HashGenerator.generateEncryptKey("peny@mail.com")));
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}