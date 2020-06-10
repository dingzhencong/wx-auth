package com.weixin.wx_auth.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {
    private Integer errcode;
    private String errmsg;
}
