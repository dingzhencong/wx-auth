package com.weixin.wx_auth.common.pojo.ai.res;

import lombok.Data;

@Data
public class AICreateResponse {
    private String db_name;
    private int size;
    private int max_size;
    private String info;
}
