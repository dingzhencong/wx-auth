package com.ding.common.utils;

import com.ding.common.JsonObject;
import com.ding.common.enums.StatusEnum;

/**
 * 普通接口对象工具类
 *
 * @author dingzhencong
 */
public class JsonObjectUtils {

    private JsonObjectUtils() {
    }

    public static JsonObject getSuccessJsonObject() {
        JsonObject jsonObject = getJsonObject();
        jsonObject.setCode(StatusEnum.SUCCESS.getType());
        jsonObject.setMessage(StatusEnum.SUCCESS.getMessage());
        return jsonObject;
    }

    public static <T> JsonObject getSuccessJsonObject(T t) {
        JsonObject jsonObject = getSuccessJsonObject();
        jsonObject.setObject(t);
        return jsonObject;
    }

    public static JsonObject getFailJsonObject() {
        JsonObject jsonObject = getJsonObject();
        jsonObject.setCode(StatusEnum.FAIL.getType());
        jsonObject.setMessage(StatusEnum.FAIL.getMessage());
        return jsonObject;
    }

    public static JsonObject getFailJsonObjectByMessage(String message) {
        JsonObject jsonObject = getFailJsonObject();
        jsonObject.setMessage(message);
        return jsonObject;
    }

    private static JsonObject getJsonObject() {
        return new JsonObject();
    }
}
