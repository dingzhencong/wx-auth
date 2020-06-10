package com.weixin.wx_auth.feign;


import com.weixin.wx_auth.common.pojo.*;
import com.weixin.wx_auth.common.pojo.response.MessageSendResponseDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "eLinkFeignClient", url = "${e-link.e-link-host}")
public interface ELinkFeignClient {

    /**
     * 获取access_token
     * @param corpid
     * @param corpsecret
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "${e-link.access-token-uri}")
    AccessTokenDTO getAccessToken(@RequestParam(value = "corpid") String corpid, @RequestParam(value = "corpsecret") String corpsecret);

    /**
     *  获取用户信息
     * @param access_token
     * @param code
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "${e-link.auth-param.user-info-uri}")
    WxUserInfo getUserInfo(@RequestParam(value = "access_token") String access_token, @RequestParam(value = "code") String code);

    /**
     * 获取用户详情信息
     * @param access_token
     * @param paramMap
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${e-link.auth-param.user-detail-uri}")
    UserDetailDTO getUserDetail(@RequestParam(value = "access_token") String access_token, @RequestBody Map paramMap);

    /**
     * 获取前端jsapiTicket
     * @param access_token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "${e-link.get-jsapi-ticket}")
    JsapiTicketDTO getJsapiTicket(@RequestParam(value = "access_token") String access_token);

    /**
     * 消息推送
     * @param access_token
     * @param messageSendDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${e-link.send-message-uri}")
    MessageSendResponseDTO messageSend(@RequestParam(value = "access_token") String access_token, @RequestBody MessageSendDTO messageSendDTO);

}
