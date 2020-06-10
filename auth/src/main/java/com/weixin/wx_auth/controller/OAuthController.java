package com.weixin.wx_auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ding.common.BusinessException;
import com.weixin.wx_auth.common.config.DingXinParamConfig;
import com.weixin.wx_auth.common.config.ELinkParamConfig;
import com.weixin.wx_auth.common.constant.OAuthConstant;
import com.weixin.wx_auth.common.pojo.UserDetailDTO;
import com.weixin.wx_auth.common.pojo.WxUserInfo;
import com.weixin.wx_auth.common.pojo.dingxin.res.BusinessSupBaseRes;
import com.weixin.wx_auth.common.pojo.dingxin.res.UserInfoRes;
import com.weixin.wx_auth.common.utils.WxUtils;
import com.weixin.wx_auth.feign.ELinkFeignClient;
import com.weixin.wx_auth.feign.FeignService;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("Duplicates")
@Controller
@RequestMapping("obs")
@Slf4j
public class OAuthController {

    @Autowired
    private RedisTemplate<Object, Object> template;

    @Autowired
    private ELinkParamConfig eLinkParamConfig;
    @Autowired
    private DingXinParamConfig dingXinParamConfig;
    @Autowired
    private ELinkFeignClient eLinkFeignClient;

    @GetMapping("index")
    public String auth() {
        String redirectUri = eLinkParamConfig.getEpHost().concat(eLinkParamConfig.getAuthParam().getRedirectUri());
        log.info("auth::{}", "开始授权");
        try {
            redirectUri = URLEncoder.encode(redirectUri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encode error::{} ", e.getMessage());
        }
        String oauthUrl = String.format(eLinkParamConfig.getAuthParam().getOauthUri(), eLinkParamConfig.getCropId(),
                redirectUri, OAuthConstant.Scope.SNSAPI_PRIVATEINFO, eLinkParamConfig.getAgentId());
        oauthUrl = eLinkParamConfig.getELinkHost().concat(oauthUrl);
        log.info("url::{}", oauthUrl);
        return "redirect:" + oauthUrl;
    }

    /**
     * elink授权回调地址
     * @param code
     * @param state
     * @return 授权成功跳转页面地址
     */
    @GetMapping(value = "/page")
    public String page(String code, String state) {
        log.info("code::{},state::{}", code, state);
        String userId = "";
        UserInfoRes userInfoRes = null;
        AtomicReference<String> employeeName = new AtomicReference<>("");
        if (!StringUtils.isEmpty(code)) {
            WxUserInfo wxUserInfo = eLinkFeignClient.getUserInfo(WxUtils.getAccessToken(), code);
            log.info("userInfo::{}", wxUserInfo);
            userInfoRes = Optional.ofNullable(wxUserInfo).map(WxUserInfo::getUser_ticket).map(ticket -> {
                UserDetailDTO userDetailDTO = eLinkFeignClient.getUserDetail(WxUtils.getAccessToken(),new HashMap<String, String>() {{
                    put("user_ticket", ticket);
                }});
                FeignService service = Feign.builder()
                        .target(FeignService.class, dingXinParamConfig.getUserInfoUrl());
                Map paramMap = new HashMap();
                String userid = "9A73241EFDDD4EEC843C4C6B5A5259FB";
                paramMap.put("userId", userid);
                String userInfoString = service.requestByGet(paramMap);
                log.info("userInfoString::{}", userInfoString);
                BusinessSupBaseRes<UserInfoRes> userInfoResBusinessSupBaseRes = JSONObject.parseObject(
                        userInfoString, new TypeReference<BusinessSupBaseRes<UserInfoRes>>(){});
                log.info("employeeName::{}", userInfoResBusinessSupBaseRes.getResponseData().getEmployeeName());
                employeeName.set(userInfoResBusinessSupBaseRes.getResponseData().getEmployeeName());
                return userInfoResBusinessSupBaseRes.getResponseData();
            }).orElseThrow(BusinessException::new);

            //TODO ...获取用户信息后进行处理
        }
        return "redirect:".concat(eLinkParamConfig.getEpHost()).concat(eLinkParamConfig.getEpHomeUri())
                .concat("?userId=").concat(userInfoRes.getUserId()).concat("&employeeName=").concat(userInfoRes.getEmployeeName())
                .concat("&mobile=").concat(userInfoRes.getMobile().concat("&account=").concat(userInfoRes.getAccount()));
    }

}
