package com.weixin.wx_auth.common.mq;


import com.alibaba.fastjson.JSONObject;
import com.weixin.wx_auth.common.config.ELinkParamConfig;
import com.weixin.wx_auth.common.constant.ActiveConstant;
import com.weixin.wx_auth.common.pojo.MessageSendDTO;
import com.weixin.wx_auth.common.pojo.response.MessageSendResponseDTO;
import com.weixin.wx_auth.common.utils.WxUtils;
import com.weixin.wx_auth.feign.ELinkFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.Objects;


/**
 * 消息消费者
 * @author dingzhencong
 *
 */
@Component
@Slf4j
public class MQMessageConsumer {

    @Autowired
    private ELinkFeignClient eLinkFeignClient;

    @Autowired
    private ELinkParamConfig eLinkParamConfig;

    /**
     * 测试往业务支撑推送消息的接收
     * @param message
     * @throws JMSException
     */
    @JmsListener(destination = ActiveConstant.SEND_MESSAGE_QUEUE_NAME)
    public void sendMessageQueue(Message message) throws JMSException {
        log.info("send接收到报文数据::{}", message.toString());
        MessageSendDTO messageSendDTO = null;
        if (message instanceof TextMessage) {
            messageSendDTO = JSONObject.parseObject(((TextMessage) message).getText(), MessageSendDTO.class);
        } else if (message instanceof ObjectMessage) {
            messageSendDTO = (MessageSendDTO) ((ObjectMessage) message).getObject(); 
        }
        log.info("send接收到报文数据::{}", messageSendDTO);
    }

    /**
     * 接收业务支撑平台的推送消息
     * @param message
     * @throws JMSException
     */
    @JmsListener(destination = ActiveConstant.ELINK_MESSAGE_QUEUE)
    public void receiveMessageQueue(Message message) throws JMSException {
        log.info("receive接收到报文数据::{}", message.toString());
        MessageSendDTO messageSendDTO = null;
        if (message instanceof TextMessage) {
            messageSendDTO = JSONObject.parseObject(((TextMessage) message).getText(), MessageSendDTO.class);
        } else if (message instanceof ObjectMessage) {
            messageSendDTO = (MessageSendDTO) ((ObjectMessage) message).getObject();
        }else {
            log.error("message格式::{}", message.getClass());
        }
        if (Objects.nonNull(messageSendDTO)) {
            messageSendDTO.setAgentid(eLinkParamConfig.getAgentId());
            MessageSendResponseDTO messageSendResponseDTO = eLinkFeignClient.messageSend(WxUtils.getAccessToken(), messageSendDTO);
            log.info("消息推送返回结果::{}", messageSendResponseDTO);
        }else {
            log.error("数据格式异常::{}", message.toString());
        }
    }
}
