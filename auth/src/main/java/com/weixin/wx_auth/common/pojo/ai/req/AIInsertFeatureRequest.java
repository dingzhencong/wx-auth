package com.weixin.wx_auth.common.pojo.ai.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIInsertFeatureRequest {
    private int feature_id;
    private String db_name;
    private String feature;
    private String info;
}
