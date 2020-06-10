package com.weixin.wx_auth.common.pojo.ai.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AICreateRequest{
    private String db_name;
    private int max_size;
    private String info;
}
