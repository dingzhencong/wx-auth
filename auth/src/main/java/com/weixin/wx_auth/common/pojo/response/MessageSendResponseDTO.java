package com.weixin.wx_auth.common.pojo.response;

import lombok.Data;

@Data
public class MessageSendResponseDTO {
    private int errcode;
    private String jobid;
    private String errmsg;
    private String invalidparty;
    private String invaliduser;
}
