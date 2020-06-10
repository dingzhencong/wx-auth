package com.ding.common.message.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    /**
     * 生成随机验证码
     * @return
     */
    public static String generateMessageCode() {
        // 生成随机6位码
        String s = "";
        while (s.length() < 6) {
            s += (int) (Math.random() * 10);
        }
        return s;
    }

    /**
     * 判断是否是手机号
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
