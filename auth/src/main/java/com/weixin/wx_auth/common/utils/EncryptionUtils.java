package com.weixin.wx_auth.common.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


public class EncryptionUtils{
    public static String aiRquestEnc(String uri, String accessId, String applicationId,
                                     String accessSecret, String time) {
        String one = uri + accessId + applicationId + "#" + time;
        Base64.Encoder encoder = Base64.getEncoder();
        String two;
        try {
            two = encoder.encodeToString(one.getBytes("UTF-8"));
            String three = HMACSHA256(two, accessSecret);
            String four =
                    encoder.encodeToString(three.getBytes("UTF-8"));
            return four;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String HMACSHA256(String data, String key) throws
            Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new
                SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array =
                sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) |
                    0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static String getImageStr(InputStream imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        //读取图片字节数组
        try
        {
            data = new byte[imgFile.available()];
            imgFile.read(data);
            imgFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
}
