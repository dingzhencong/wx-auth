package com.weixin.wx_auth.feign;


import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

/**
 * Feign调用接口
 *
 * @author dingzhencong
 * @date 2019年11月14日
 */

public interface FeignService {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST")
    String requestByPost(String requestString);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST")
    String requestByPost(Object object);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("GET")
    String requestByGet(@QueryMap Map<String, Object> queryMap);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET")
    String requestByGet(@QueryMap Object object);
}
