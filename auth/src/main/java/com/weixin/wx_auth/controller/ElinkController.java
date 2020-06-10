package com.weixin.wx_auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.ding.common.JsonObject;
import com.weixin.wx_auth.common.mq.MQMessageProducer;
import com.weixin.wx_auth.common.pojo.MessageSendDTO;
import com.weixin.wx_auth.common.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import static com.weixin.wx_auth.common.constant.ActiveConstant.SEND_MESSAGE_QUEUE_NAME;


@RestController
@RequestMapping("base")
@Slf4j
public class ElinkController {

    @Autowired
    private MQMessageProducer producer;

    /**
     * 获取前端ticket
     * @return 返回jsapiTicket
     */
    @GetMapping("getJsapiTicket")
    public JsonObject getJsapiTicket() {
        String ticket = WxUtils.getJsapiTicket();
        return JsonObject.builder().object(ticket).build();
    }

    @PostMapping("testPushMessage")
    public JsonObject testPushMessage(@RequestBody MessageSendDTO messageSendDTO) {
        producer.sendMessage(SEND_MESSAGE_QUEUE_NAME, messageSendDTO);
        producer.sendMessage(SEND_MESSAGE_QUEUE_NAME, JSONObject.toJSONString(messageSendDTO));
        return JsonObject.builder().object("").build();
    }
}
