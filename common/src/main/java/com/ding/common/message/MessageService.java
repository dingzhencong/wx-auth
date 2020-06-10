package com.ding.common.message;

import com.ding.common.JsonObject;
import com.ding.common.message.constant.MessageConstant;
import com.ding.common.message.enums.MessageEnum;
import com.ding.common.message.enums.MessageStatusEnum;
import com.ding.common.message.util.MessageUtil;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dingzhencong
 * @date 2019年12月25日
 */
@Component("messageService")
public class MessageService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public MessageStatusEnum send(String phoneNumber, MessageEnum messageEnum) {
        String redisKey = phoneNumber.concat(messageEnum.getCode());
        if (!MessageUtil.isMobile(phoneNumber)) {
            return MessageStatusEnum.PHONE_ERROR;
        } else if (redisTemplate.hasKey(redisKey)) {
            return MessageStatusEnum.MESSAGE_EXIT;
        }

        //TODO 模拟调用业务支撑平台接口
        FeignService service = Feign.builder().decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder()).target(FeignService.class, "http://172.16.12.168:9080/elink/test/generateMessageCode");
        String result = service.requestByGet(new HashMap(8) {{
            put("phone", phoneNumber);
            put("text", MessageConstant.messageMap.get(messageEnum.getCode()));
        }});

        redisTemplate.opsForValue().set(redisKey, result, MessageConstant.expire, TimeUnit.SECONDS);

        return MessageStatusEnum.SEND_SUUCESS;
    }

    /**
     * @param phoneNumber 验证的手机号码
     * @param code 验证码
     * @param messageEnum 渠道类型
     * @return MessageStatusEnum
     */
    public MessageStatusEnum validate(String phoneNumber, String code, MessageEnum messageEnum) {
        String redisKey = phoneNumber.concat(messageEnum.getCode());
        if (!MessageUtil.isMobile(phoneNumber)) {
            return MessageStatusEnum.PHONE_ERROR;
        } else if (!redisTemplate.hasKey(redisKey)) {
            return MessageStatusEnum.MESSAGE_CODE_EXPIRED;
        }
        String redisCode = redisTemplate.opsForValue().get(redisKey);

        if (redisCode.equals(code)) {
            redisTemplate.delete(redisKey);
            return MessageStatusEnum.MESSAGE_CODE_VALIDATE_SUCCESS;
        } else {
            return MessageStatusEnum.MESSAGE_CODE_VALIDATE_ERROR;
        }
    }
}
