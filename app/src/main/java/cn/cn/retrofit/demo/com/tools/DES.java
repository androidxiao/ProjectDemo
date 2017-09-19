package cn.cn.retrofit.demo.com.tools;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by chawei on 2017/9/14.
 */

public class DES {
    private static Key key;
    private static Cipher cipher;

    private DES() {}

    private static DES getInstance(String key) throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        return getInstance(getKeyByStr(key));
    }

    private static DES getInstance(byte key[]) throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        DES des = new DES();
        if (des.key == null) {
            SecretKeySpec spec = new SecretKeySpec(key, "DES");
            des.key = spec;
        }
        des.cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        return des;
    }

    private byte[] encrypt(byte b[]) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException,
            IllegalStateException {
        byte byteFina[] = null;
        cipher.init(1, key);
        byteFina = cipher.doFinal(b);
        return byteFina;
    }

    private byte[] decrypt(byte b[]) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException,
            IllegalStateException {
        byte byteFina[] = null;
        cipher.init(2, key);
        byteFina = cipher.doFinal(b);
        return byteFina;
    }

    private static byte[] getKeyByStr(String str) {
        byte bRet[] = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
                    + getChrInt(str.charAt(2 * i + 1)));
            bRet[i] = itg.byteValue();
        }
        return bRet;
    }

    private static int getChrInt(char chr) {
        int iRet = 0;
        if (chr == "0".charAt(0))
            iRet = 0;
        if (chr == "1".charAt(0))
            iRet = 1;
        if (chr == "2".charAt(0))
            iRet = 2;
        if (chr == "3".charAt(0))
            iRet = 3;
        if (chr == "4".charAt(0))
            iRet = 4;
        if (chr == "5".charAt(0))
            iRet = 5;
        if (chr == "6".charAt(0))
            iRet = 6;
        if (chr == "7".charAt(0))
            iRet = 7;
        if (chr == "8".charAt(0))
            iRet = 8;
        if (chr == "9".charAt(0))
            iRet = 9;
        if (chr == "A".charAt(0))
            iRet = 10;
        if (chr == "B".charAt(0))
            iRet = 11;
        if (chr == "C".charAt(0))
            iRet = 12;
        if (chr == "D".charAt(0))
            iRet = 13;
        if (chr == "E".charAt(0))
            iRet = 14;
        if (chr == "F".charAt(0))
            iRet = 15;
        return iRet;
    }

    /**
     * @interpret 进行base64加密操作
     * @param text
     * @return String
     */
    private static String encrypt(String text) {
        String body = null;

        try {
            DES des = DES.getInstance(getKey());
            byte[] b = des.encrypt(text.getBytes("UTF8"));
            body = new String(Base64.encode(b, Base64.DEFAULT));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return body;
    }

    private static String decode() {
        //anxindeli_cow_jtwmy_33ccab1
        return authCode("anxindeli_cow_jtwmy_33ccab1", "DECODE");
    }


    /**
     * @interpret 进行base64进行解密
     * @param text
     * @return String
     */
    private static String decrypt(String text) {
        String body = null;
        try {
            DES des = DES.getInstance(getKey());
            byte[] b = Base64.decode(text.getBytes(), Base64.DEFAULT);
            body = new String(des.decrypt(b), "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    private static String getKey() {
        return "0!1@2#3$4%5&6*7a";
    }

    /**
     *
     * @param content  内容
     * @param operation 加密或解密
     * @return
     */
    private static String authCode(String content, String operation){
        String encontent = null;
        if (operation != null && operation.equals("DECODE")) {
            encontent = encrypt(content);
        } else if (operation != null && operation.equals("ENCODE")) {
            encontent = decrypt(content);
        }
        return encontent;
    }

    public static String encode() {
        return authCode(decode(), "ENCODE");
    }
}
