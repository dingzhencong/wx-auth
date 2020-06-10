package com.weixin.wx_auth.common.pojo.dingxin.res;

import lombok.Data;

/**
 * @author hp
 */
@Data
public class BusinessSupBaseRes<T> {
    private T responseData;
    private String responseInfo;
    private String responseCode;
}
