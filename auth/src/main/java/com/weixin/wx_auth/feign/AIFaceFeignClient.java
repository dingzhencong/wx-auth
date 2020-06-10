package com.weixin.wx_auth.feign;



import com.weixin.wx_auth.common.config.HeaderInterceptor;
import com.weixin.wx_auth.common.pojo.ai.req.*;
import com.weixin.wx_auth.common.pojo.ai.res.AIBaseResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AICreateResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AIInsertResponse;
import com.weixin.wx_auth.common.pojo.ai.res.AISearchResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "aiFaceFeignClient", url = "${ai-face.host}", configuration = HeaderInterceptor.class)
public interface AIFaceFeignClient {
    /**
     * 该API⽤于创建⼀个特征库。
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.create}")
    AIBaseResponse<List<AICreateResponse>> create(@RequestBody AICreateRequest aiCreateRequest);


    /**
     * 该API⽤于删除⼀个指定特征库(被删除的特征库内所有特征信息将⼀并被删除)
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.remove}")
    AIBaseResponse<List<AICreateResponse>> remove(@RequestBody AICreateRequest aiCreateRequest);

    /**
     * 该API⽤于修改特征库⼤⼩。
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.update}")
    AIBaseResponse<List<AICreateResponse>> update(@RequestBody AICreateRequest aiCreateRequest);

    /**
     * 该API⽤于查询指定特征库信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.info}")
    AIBaseResponse<List<AICreateResponse>> info(@RequestBody AICreateRequest aiCreateRequest);

    /**
     * 该API⽤于使⽤图⽚⽂件向指定特征库内插⼊⼀条特征信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.insert}")
    AIBaseResponse<List<AIInsertResponse>> insert(@RequestBody AIInsertRequest aiInsertRequest);

    /**
     * 该API⽤于删除指定特征库内的某条特征信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.delete}")
    AIBaseResponse<List<AIInsertResponse>> delete(@RequestBody AIDeleteRequest aiDeleteRequest);

    /**
     * 该API⽤于查询指定特征库内的某条特征信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.get}")
    AIBaseResponse<List<AIInsertResponse>> get(@RequestBody AIDeleteRequest aiDeleteRequest);

    /**
     * 该API⽤于使⽤特征字符串向指定特征库内插⼊⼀条特征信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.insert-feature}")
    AIBaseResponse<List<AIInsertResponse>> insertFeature(@RequestBody AIInsertFeatureRequest aiInsertFeatureRequest);

    /**
     * ⽤于使⽤图⽚来进⾏⼈脸搜索，返回指定特征库内最相似的K条特征信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.search}")
    AIBaseResponse<List<AISearchResponse>> search(@RequestBody AISearchRequest aiSearchRequest);

    /**
     * ⽤于使⽤特征字符串来进⾏⼈脸搜索，返回指定特征库内最相似的K条特征信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "${ai-face.search-feature}")
    AIBaseResponse<List<AISearchResponse>> searchFeature(@RequestBody AISearchRequest aiSearchRequest);

}
