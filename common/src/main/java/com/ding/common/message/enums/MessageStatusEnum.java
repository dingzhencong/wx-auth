package com.ding.common.message.enums;

/**
 * 短信相关枚举类
 * @author dingzhencong
 */
public enum MessageStatusEnum {
    /**
     * 校验成功
     */
    MESSAGE_CODE_VALIDATE_SUCCESS(5, "校验成功"),
    /**
     * 手机验证码不匹配
     */
    MESSAGE_CODE_VALIDATE_ERROR(4, "手机验证码错误"),
    /**
     * 手机验证码过期
     */
    MESSAGE_CODE_EXPIRED(3, "手机验证码过期"),
    /**
     * 手机号码错误
     */
    PHONE_ERROR(2, "手机号码错误"),
    /**
     * 短信已经存在
     */
    MESSAGE_EXIT(1, "不能重复发送短信"),
    /**
     * 短信发送成功
     */
    SEND_SUUCESS(0, "短信发送成功");

    MessageStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String desc;
    private Integer code;


    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * @param description
     * @return
     */
    public static MessageStatusEnum getEnumByDesc(String description) {
        for (MessageStatusEnum roleEnum: values()) {
            if(roleEnum.getDesc().equals(description)) {
                return roleEnum;
            }
        }
        return null;
    }
}
