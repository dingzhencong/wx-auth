package com.weixin.wx_auth.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AccessTokenDTO extends BaseDTO{
    private String access_token;
    private int expires_in;

}
