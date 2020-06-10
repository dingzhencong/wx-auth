package com.weixin.wx_auth.common.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WxUserInfo extends BaseDTO implements Serializable {
    private String user_ticket;
    private int usertype;
    private String userid;
    private String deviceid;
    private int expires_in;
}
