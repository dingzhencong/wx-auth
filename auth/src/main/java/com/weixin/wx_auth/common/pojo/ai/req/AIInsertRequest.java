package com.weixin.wx_auth.common.pojo.ai.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIInsertRequest {
    private int feature_id;
    private String image;
    private String db_name;
    private String info;
}
