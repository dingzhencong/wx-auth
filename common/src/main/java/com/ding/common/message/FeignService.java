package com.ding.common.message;


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
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("GET")
    String requestByGet(@QueryMap Map queryMap);
}
