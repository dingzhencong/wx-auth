package com.weixin.wx_auth.common.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TestDTO implements Serializable {
    private Integer code;
    private String message;
}
