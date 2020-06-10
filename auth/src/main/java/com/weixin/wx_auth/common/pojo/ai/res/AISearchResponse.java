package com.weixin.wx_auth.common.pojo.ai.res;

import lombok.Data;

@Data
public class AISearchResponse {
    private int feature_id;
    private double score;
    private String info;
}
