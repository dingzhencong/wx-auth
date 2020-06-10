package com.ding.common.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 消息生产者
 * @author dingzhencong
 *
 */
@Component
@Slf4j
public class MQMessageProducer<T extends Serializable> {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveQueueFactory activeQueueFactory;

    /**
     * MQ消息推送
     * @param queueName 推送队列的名称
     * @param data 推送数据
     */
    public void sendMessage(String queueName,T data){
        jmsTemplate.send(activeQueueFactory.getQueueByName(queueName), session -> {
            if (data instanceof String) {
                return session.createTextMessage((String) data);
            }
            return session.createObjectMessage(data);
        });
    }
}
