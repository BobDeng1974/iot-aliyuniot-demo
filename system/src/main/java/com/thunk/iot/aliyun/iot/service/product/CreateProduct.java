package com.thunk.iot.aliyun.iot.service.product;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.CreateProductRequest;
import com.aliyuncs.iot.model.v20180120.CreateProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:16
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class CreateProduct {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 新建产品
     * @param productName 产品命名
     * @param nodeType 产品的节点类型 0：设备 1：网关
     * @param dataFormat 0：透传/自定义格式（CUSTOM_FORMAT） 1：Alink协议（ALINK_FORMAT）。
     * @param description 描述信息
     * @param netType 联网方式 WIFI、CELLULAR、ETHERNET、OTHER
     * @return
     * @throws ClientException
     */
    public Object createProduct(String productName, Integer nodeType, Integer dataFormat, String description, String netType) {
        CreateProductRequest request = new CreateProductRequest();
        request.setProductName(productName);
        request.setNodeType(nodeType);
        request.setDataFormat(dataFormat);
        request.setDescription(description);
        request.setNetType(netType);
        CreateProductResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
