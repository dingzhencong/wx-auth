package com.weixin.wx_auth.controller;


import com.ding.common.JsonObject;
import com.weixin.wx_auth.common.pojo.ai.req.AICreateRequest;
import com.weixin.wx_auth.common.pojo.ai.req.AIInsertRequest;
import com.weixin.wx_auth.common.pojo.ai.req.AISearchRequest;
import com.weixin.wx_auth.common.utils.EncryptionUtils;
import com.weixin.wx_auth.feign.AIFaceFeignClient;
import com.weixin.wx_auth.service.AiFaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Ai人脸识别相关接口
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("ai")
@Slf4j
public class AiFaceController {
    @Autowired
    private AiFaceService service;

    @Autowired
    private AIFaceFeignClient feignClient;

    @PostMapping("create")
    public JsonObject create(@RequestBody AICreateRequest aiCreateRequest) {
        JsonObject jsonObject = service.create(aiCreateRequest);
        return jsonObject;
    }

    @PostMapping("insert")
    public JsonObject insert(String dbName, Integer featureId, String info, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        AIInsertRequest aiInsertRequest = AIInsertRequest.builder().db_name(dbName).feature_id(featureId)
                .info(info).image(EncryptionUtils.getImageStr(imageFile.getInputStream())).build();
        JsonObject jsonObject = service.insert(aiInsertRequest);
        return jsonObject;
    }

    @PostMapping("search")
    public JsonObject search(String dbName,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        AISearchRequest aiSearchRequest = AISearchRequest.builder().db_name(dbName)
                .image(EncryptionUtils.getImageStr(imageFile.getInputStream())).build();
        JsonObject jsonObject = service.search(aiSearchRequest);
        return jsonObject;
    }
}
