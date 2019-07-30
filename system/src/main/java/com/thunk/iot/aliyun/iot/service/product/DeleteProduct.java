package com.thunk.iot.aliyun.iot.service.product;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.DeleteProductRequest;
import com.aliyuncs.iot.model.v20180120.DeleteProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:32
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class DeleteProduct {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 删除指定产品
     * @param productKey 要删除的产品Key
     * @return
     * @throws ClientException
     */
    public Object deleteProduct(String productKey) {
        DeleteProductRequest request = new DeleteProductRequest();
        request.setProductKey(productKey);
        DeleteProductResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
