package com.ding.common.enums;


/**
 * 接口响应状态枚举类
 * @author dingzhencong
 */
public enum StatusEnum {
    /**
     * 失败状态枚举
     */
    FAIL(1,"失败"),
    /**
     * 成功状态枚举
     */
    SUCCESS(0, "成功");

    private Integer type;
    private String message;

    StatusEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
