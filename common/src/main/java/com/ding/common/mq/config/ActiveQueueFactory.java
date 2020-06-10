package com.ding.common.mq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.jms.Queue;
import java.util.Map;

import static com.ding.common.mq.constant.ActiveConstant.*;


/**
 * 消息队列工厂类
 * @author dingzhencong
 * @date 2019年12月11日
 */
@Component
public class ActiveQueueFactory {

    @Value("${activeMq.sendMessageQueueName:'SEND_MESSAGE'}")
    private String sendMessageToEPQueueName;

    @Bean(name = SEND_MESSAGE_TO_EP_QUEUE_NAME)
    public Queue sendMessageQueue() {
        return new ActiveMQQueue(sendMessageToEPQueueName);
    }

    @Bean(name= ELINK_MESSAGE_QUEUE)
    public Queue receiveMessageQueue() {
        return new ActiveMQQueue(ELINK_MESSAGE_QUEUE);
    }

    @Autowired
    private Map<String, Queue> queueMapMap;

    public Queue getQueueByName(String name) {

        queueMapMap.forEach((k, v) -> System.out.println(k + "===" + v));
        return queueMapMap.get(name);
    }
}