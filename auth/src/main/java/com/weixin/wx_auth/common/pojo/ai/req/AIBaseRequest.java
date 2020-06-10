package com.weixin.wx_auth.common.pojo.ai.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIBaseRequest {
    private String accessId;
    private String applicationId;
    private String accessToken;
    private String time;
}
