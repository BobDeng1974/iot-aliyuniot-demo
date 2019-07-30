package com.thunk.iot.aliyun.iot.service.device;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryDeviceRequest;
import com.aliyuncs.iot.model.v20180120.QueryDeviceResponse;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
import com.thunk.iot.aliyun.iot.service.product.QueryProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaolei
 * @Version 1.0.0
 * @Description:
 * @Date: 2019/5/9 10:44
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class QueryDevice {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 查询指定产品下的所有设备列表
     *
     * @param productKey  要查询的设备所隶属的产品Key
     * @param pageSize    指定返回结果中每页显示的记录数量，最大值是50。默认值是10。
     * @param currentPage 指定显示返回结果中的第几页的内容。默认值是 1。
     * @return
     * @throws ClientException
     */
    public QueryDeviceResponse queryDevice(String productKey, Integer pageSize, Integer currentPage) {
        QueryDeviceRequest request = new QueryDeviceRequest();
        request.setProductKey(productKey);
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        QueryDeviceResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 查询所有设备（自定义循环查询）
     *
     * @param productKey
     * @param pageSize
     * @param currentPage
     * @return
     */
    @Autowired
    private QueryProductList queryProductList;

    public List queryAllDevice(Integer pageSize, Integer currentPage) {
        QueryProductListResponse response = queryProductList.queryProductListTwo(1, 100);

        List<QueryProductListResponse.Data.ProductInfo> list = response.getData().getList();

        List productKey = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            productKey.add(list.get(i).getProductKey());
        }

        List result = new ArrayList<>();

        for (int i = 0; i < productKey.size(); i++) {
            QueryDeviceResponse queryDevice = queryDevice((String) productKey.get(i), 100, 1);
            List<QueryDeviceResponse.DeviceInfo> data = queryDevice.getData();
            for (int j = 0; j < data.size(); j++) {
                result.add(data.get(j));
            }
        }

        return result;
    }

}
