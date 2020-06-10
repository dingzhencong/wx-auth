package com.weixin.wx_auth.common.pojo.ai.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AISearchRequest {
    private String image;
    private String db_name;
    private String feature;
    private int top;
}
