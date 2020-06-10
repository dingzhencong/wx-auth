package com.weixin.wx_auth.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * ai人脸识别参数配置类
 * @author dingzhencong
 * @date 2019年12月12日
 */
@Component
@PropertySource("classpath:application-${spring.profiles.active}.yml")
@ConfigurationProperties(prefix = "ai-face")
@Data
public class AIFaceParamConfig {
    private String host;
    private String accessId;
    private String applicationId;
    private String accessSecret;
    private String create;
    private String remove;
    private String update;
    private String info;
    private String insert;
    private String get;
    private String insertFeature;
    private String searchFeature;
    private String search;
    private String deviceUpdate;
    private String deviceDelete;
    private String deviceList;
}
