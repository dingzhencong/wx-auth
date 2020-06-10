package com.weixin.wx_auth.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 鼎信相关参数配置类
 * @author dingzhencong
 * @date 2019年12月24日
 */
@Component
@PropertySource("classpath:application-${spring.profiles.active}.yml")
@ConfigurationProperties(prefix = "ding-xin")
@Data
public class DingXinParamConfig {
    private String userInfoUrl;
}
