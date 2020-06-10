package com.weixin.wx_auth.service;

import com.ding.common.JsonObject;

import com.weixin.wx_auth.common.pojo.ai.req.*;
import com.weixin.wx_auth.common.pojo.ai.res.AIBaseResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AICreateResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AIInsertResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AISearchResponse;
import com.weixin.wx_auth.feign.AIFaceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AiFaceService {
    @Autowired
    private AIFaceFeignClient feignClient;

    public JsonObject create(AICreateRequest aiCreateRequest) {
        log.info("create::{}", aiCreateRequest);
        AIBaseResponse<List<AICreateResponse>> aiBaseResponse = feignClient.create(aiCreateRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject remove(AICreateRequest aiCreateRequest) {
        log.info("remove::{}", aiCreateRequest);
        AIBaseResponse<List<AICreateResponse>> aiBaseResponse = feignClient.remove(aiCreateRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject update(AICreateRequest aiCreateRequest) {
        log.info("update::{}", aiCreateRequest);
        AIBaseResponse<List<AICreateResponse>> aiBaseResponse = feignClient.update(aiCreateRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject info(AICreateRequest aiCreateRequest) {
        log.info("info::{}", aiCreateRequest);
        AIBaseResponse<List<AICreateResponse>> aiBaseResponse = feignClient.info(aiCreateRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject insert(AIInsertRequest aiInsertRequest) {
        log.info("insert::{}", aiInsertRequest);
        AIBaseResponse<List<AIInsertResponse>> aiBaseResponse = feignClient.insert(aiInsertRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject delete(AIDeleteRequest aiDeleteRequest) {
        log.info("delete::{}", aiDeleteRequest);
        AIBaseResponse<List<AIInsertResponse>> aiBaseResponse = feignClient.delete(aiDeleteRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject get(AIDeleteRequest aiDeleteRequest) {
        log.info("get::{}", aiDeleteRequest);
        AIBaseResponse<List<AIInsertResponse>> aiBaseResponse = feignClient.get(aiDeleteRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject insertFeature(AIInsertFeatureRequest aiInsertFeatureRequest) {
        log.info("insertFeature::{}", aiInsertFeatureRequest);
        AIBaseResponse<List<AIInsertResponse>> aiBaseResponse = feignClient.insertFeature(aiInsertFeatureRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject search(AISearchRequest aiSearchRequest) {
        log.info("search::{}", aiSearchRequest);
        AIBaseResponse<List<AISearchResponse>> aiBaseResponse = feignClient.search(aiSearchRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }

    public JsonObject searchFeature(AISearchRequest aiSearchRequest) {
        log.info("searchFeature::{}", aiSearchRequest);
        AIBaseResponse<List<AISearchResponse>> aiBaseResponse = feignClient.searchFeature(aiSearchRequest);
        return JsonObject.builder().object(aiBaseResponse).build();
    }
}
