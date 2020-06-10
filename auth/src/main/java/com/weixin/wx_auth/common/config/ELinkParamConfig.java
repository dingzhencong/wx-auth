package com.weixin.wx_auth.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * elink参数配置类
 * @author dingzhencong
 * @date 2019年12月4日
 */
@Component
@PropertySource("classpath:application-${spring.profiles.active}.yml")
@ConfigurationProperties(prefix = "e-link")
@Data
public class ELinkParamConfig {
    private Integer agentId;
    private String secret;
    private String cropId;
    private String eLinkHost;
    private String epHost;
    /**
     * 员工工作台首页地址
     */
    private String epHomeUri ;
    /**
     * 获取accessToken地址
     */
    private String accessTokenUri;
    private AuthParam authParam;

    @Data
    public static class AuthParam{
        /**
         * oauth2.0 授权地址
         */
        private String oauthUri;
        /**
         * 授权回调地址
         */
        private String redirectUri;
        /**
         * 获取用户信息地址
         */
        private String userInfoUri;
        /**
         * 获取用户详情信息地址
         */
        private String userDetailUri;
    }
}
