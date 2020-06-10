package com.ding.common.message.enums;

/**
 * 短信相关枚举类
 * @author dingzhencong
 */
public enum MessageEnum {

    /**
     * PC端登陆
     */
    PC_LOGIN(2,"PC_LOGIN", "PC端登陆"),
    /**
     * H5短信登陆
     */
    H5_LOGIN(1,"H5_LOGIN", "H5短信登陆");

    MessageEnum(Integer type, String code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    private Integer type;
    private String desc;
    private String code;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

    /**
     * @param description
     * @return
     */
    public static MessageEnum getEnumByDesc(String description) {
        for (MessageEnum roleEnum: values()) {
            if(roleEnum.getDesc().equals(description)) {
                return roleEnum;
            }
        }
        return null;
    }
}
