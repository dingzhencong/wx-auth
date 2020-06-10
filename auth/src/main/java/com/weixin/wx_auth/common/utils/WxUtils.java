package com.weixin.wx_auth.common.utils;


import com.ding.common.utils.ExceptionUtils;
import com.weixin.wx_auth.common.config.ELinkParamConfig;
import com.weixin.wx_auth.common.constant.OAuthConstant;
import com.weixin.wx_auth.common.pojo.AccessTokenDTO;
import com.weixin.wx_auth.common.pojo.JsapiTicketDTO;
import com.weixin.wx_auth.feign.ELinkFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 微信工具类
 * @author dingzhencong
 * @date 2019年12月4日
 */
@Slf4j
@Component
public class WxUtils {
    @Autowired
    public WxUtils(RedisTemplate<Object, Object> redisTemplate, com.weixin.wx_auth.common.config.ELinkParamConfig eLinkParamConfig, ELinkFeignClient eLinkFeignClient) {
        WxUtils.redisTemplate = redisTemplate;
        WxUtils.ELinkParamConfig = eLinkParamConfig;
        WxUtils.eLinkFeignClient = eLinkFeignClient;
    }

    private static RedisTemplate<Object, Object> redisTemplate;
    private static ELinkParamConfig ELinkParamConfig;
    private static ELinkFeignClient eLinkFeignClient;

    public static String getAccessToken() {
        String token = (String) redisTemplate.opsForValue().get(OAuthConstant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            AccessTokenDTO accessTokenDTO = eLinkFeignClient.getAccessToken(ELinkParamConfig.getCropId(), ELinkParamConfig.getSecret());
            token = Optional.ofNullable(accessTokenDTO)
                    .filter(accessToken -> OAuthConstant.STATUS_CODE.equals(accessToken.getErrcode())).map(AccessTokenDTO::getAccess_token)
                    .orElseThrow(() -> ExceptionUtils.throwException("获取access_token返回数据异常::{}", accessTokenDTO, log));
            log.info("AccessTokenDTO::{}", accessTokenDTO);
            redisTemplate.opsForValue().set(OAuthConstant.ACCESS_TOKEN, token, accessTokenDTO.getExpires_in(), TimeUnit.SECONDS);
        }
        return token;
    }

    public static String getJsapiTicket() {
        String ticket = (String) redisTemplate.opsForValue().get(OAuthConstant.JSAPI_TICKET);
        if (StringUtils.isEmpty(ticket)) {
            String accessToken = getAccessToken();
            log.info("accessToken::{}", accessToken);
            JsapiTicketDTO jsapiTicketDTO = eLinkFeignClient.getJsapiTicket(accessToken);
            ticket = Optional.ofNullable(jsapiTicketDTO)
                    .filter(jsapiTicket -> OAuthConstant.STATUS_CODE.equals(jsapiTicket.getErrcode())).map(JsapiTicketDTO::getTicket)
                    .orElseThrow(() -> ExceptionUtils.throwException("获取JsapiTicket返回数据异常::{}", jsapiTicketDTO, log));
            log.info("JsapiTicketDTO::{}", jsapiTicketDTO);
            redisTemplate.opsForValue().set(OAuthConstant.JSAPI_TICKET, ticket, jsapiTicketDTO.getExpires_in(), TimeUnit.SECONDS);
        }
        return ticket;
    }
}
