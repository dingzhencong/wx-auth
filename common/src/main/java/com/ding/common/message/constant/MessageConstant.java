package com.ding.common.message.constant;

import com.ding.common.message.enums.MessageEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingzhencong
 * @date 2019年12月25日
 */
public class MessageConstant {
    private MessageConstant(){}

    public static final Map messageMap = new HashMap<String, String>(){{
        put("H5_LOGIN", "你的验证码为%s，有效期2分钟。");
        put("PC_LOGIN", "你的验证码为%s，有效期2分钟。");
    }};

    public static final Integer expire = 5 * 60;
}
