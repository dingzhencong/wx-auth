package com.ding.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通接口响应对象
 * @author dingzhencong
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonObject<T> {
    private int code = 0;
    private String message;
    private T object;
    public JsonObject(T object) {
        this.object = object;
    }
}
