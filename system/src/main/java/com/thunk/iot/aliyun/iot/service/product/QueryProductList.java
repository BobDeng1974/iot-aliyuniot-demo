package com.thunk.iot.aliyun.iot.service.product;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryProductListRequest;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
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
public class QueryProductList {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 查看所有产品列表
     * @return
     * @throws ClientException
     */
    public Object queryProductList(Integer currentPage, Integer pageSize) {
        QueryProductListRequest request = new QueryProductListRequest();
        request.setCurrentPage(currentPage);
        request.setPageSize(pageSize);
        QueryProductListResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }


    public QueryProductListResponse queryProductListTwo(Integer currentPage, Integer pageSize) {
        QueryProductListRequest request = new QueryProductListRequest();
        request.setCurrentPage(currentPage);
        request.setPageSize(pageSize);
        QueryProductListResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
