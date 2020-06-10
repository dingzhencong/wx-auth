package com.weixin.wx_auth.common.constant;

/**
 * elink 授权相关常量
 * @author dingzhencong
 * @date 2019年12月4日
 */
public class OAuthConstant {
    private OAuthConstant(){}

    public static final String RESPONSE_TYPE = "code";

    /**
     * TOKEN缓存的key
     */
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    /**
     * JSAPI_TICKET缓存的key
     */
    public static final String JSAPI_TICKET = "JSAPI_TICKET";

    /**
     * 微信返回正确的状态码
     */
    public static final Integer STATUS_CODE = 0;

    /**
     * 应用授权作用域
     */
    public static class Scope{

        /**
         * 静默授权
         * 可获取成员的基础信息
         */
        public static final String SNSAPI_BASE = "snsapi_base";
        /**
         * 静默授权
         * 可获取成员的详细信息，但不包含手机、邮箱
         */
        public static final String SNSAPI_USERINFO = "snsapi_userinfo";
        /**
         * 手动授权
         * 可获取成员的详细信息，包含手机、邮箱
         */
        public static final String SNSAPI_PRIVATEINFO = "snsapi_privateinfo";
    }
}
