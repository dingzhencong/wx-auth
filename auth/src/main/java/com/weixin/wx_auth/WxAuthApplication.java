package com.weixin.wx_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableFeignClients(basePackages = {"com.dingxin.ep.feign"})
@SpringBootApplication
@ComponentScan(basePackages = {"com.ding.common.message", "com.ding.common.config", "com.dingxin"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.ding.common.mq.*")})
public class WxAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxAuthApplication.class, args);
    }

}
