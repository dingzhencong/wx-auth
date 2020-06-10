package com.weixin.wx_auth.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendDTO implements Serializable {
    private int agentid;
    private String touser;
    private String totag;
    private TextEntity text;
    private String msgtype;
    private String toparty;

    @Data
    public static class TextEntity implements Serializable{
        private String content;
    }
}
