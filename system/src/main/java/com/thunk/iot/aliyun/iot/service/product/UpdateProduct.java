package com.thunk.iot.aliyun.iot.service.product;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.UpdateProductRequest;
import com.aliyuncs.iot.model.v20180120.UpdateProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:27
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class UpdateProduct {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 修改指定产品的信息
     * @param productKey 要修改信息的产品的Key
     * @param productName 修改后的产品名称
     * @param description 修改后的产品描述信息
     * @return
     * @throws ClientException
     */
    public Object updateProduct(String productKey, String productName, String description) {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductKey(productKey);
        request.setProductName(productName);
        request.setDescription(description);
        UpdateProductResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
