package com.weixin.wx_auth.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class JsapiTicketDTO extends BaseDTO{
    private String ticket;
    private int expires_in;

}
