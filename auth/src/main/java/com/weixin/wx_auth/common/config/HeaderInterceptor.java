package com.weixin.wx_auth.common.config;

import com.weixin.wx_auth.common.utils.EncryptionUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HeaderInterceptor implements RequestInterceptor {

    @Autowired
    private AIFaceParamConfig paramConfig;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String uri = requestTemplate.url();
        log.info("请求uri::{}", requestTemplate.url());
        String accessId = paramConfig.getAccessId();
        String applicationId = paramConfig.getApplicationId();
        String accessSecret = paramConfig.getAccessSecret();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String accessToken = EncryptionUtils.aiRquestEnc(uri, accessId, applicationId, accessSecret, time);

        requestTemplate.header("accessId", accessId);
        requestTemplate.header("applicationId", applicationId);
        requestTemplate.header("accessToken", accessToken);
        requestTemplate.header("time", time);

        log.info("header::{}", requestTemplate.headers());
    }
}
