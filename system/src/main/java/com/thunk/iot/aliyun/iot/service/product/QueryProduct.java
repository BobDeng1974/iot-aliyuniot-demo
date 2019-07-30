package com.thunk.iot.aliyun.iot.service.product;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryProductRequest;
import com.aliyuncs.iot.model.v20180120.QueryProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:09
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class QueryProduct {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 查询指定产品的详细信息
     * @param productKey 要查询的产品的ProductKey
     * @return
     * @throws ClientException
     */
    public Object queryProduct(String productKey) {
        QueryProductRequest request = new QueryProductRequest();
        request.setProductKey(productKey);
        QueryProductResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
