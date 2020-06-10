package com.weixin.wx_auth.common.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Map;

import static com.weixin.wx_auth.common.constant.ActiveConstant.ELINK_MESSAGE_QUEUE;
import static com.weixin.wx_auth.common.constant.ActiveConstant.SEND_MESSAGE_QUEUE_NAME;


/**
 * 消息队列工厂类
 * @author dingzhencong
 * @date 2019年12月11日
 */
@Component
public class ActiveQueueFactory {

    @Bean(name= SEND_MESSAGE_QUEUE_NAME)
    public Queue sendMessageQueue() {
        return new ActiveMQQueue(SEND_MESSAGE_QUEUE_NAME);
    }

    @Bean(name= ELINK_MESSAGE_QUEUE)
    public Queue receiveMessageQueue() {
        return new ActiveMQQueue(ELINK_MESSAGE_QUEUE);
    }

    @Autowired
    private Map<String, Queue> queueMapMap;

    public Queue getQueueByName(String name) {
        return queueMapMap.get(name);
    }
}