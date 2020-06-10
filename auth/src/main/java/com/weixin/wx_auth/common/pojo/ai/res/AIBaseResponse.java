package com.weixin.wx_auth.common.pojo.ai.res;

import lombok.Data;

/**
 * AI人脸识别返回基类
 */
@Data
public class AIBaseResponse<T> {
    private T result;
    private String error;
    private String request_id;
}
