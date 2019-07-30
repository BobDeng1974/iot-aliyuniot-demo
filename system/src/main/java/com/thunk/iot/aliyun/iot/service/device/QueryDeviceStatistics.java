package com.thunk.iot.aliyun.iot.service.device;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryDeviceStatisticsRequest;
import com.aliyuncs.iot.model.v20180120.QueryDeviceStatisticsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaolei
 * @Version 1.0.0
 * @Description:
 * @Date: 2019/5/9 11:10
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class QueryDeviceStatistics {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * @param //productKey 要查询的设备所隶属的产品Key。
     *                     传入产品Key，将返回该产品下的设备统计数据。
     *                     不传入该参数，则返回账号下所有设备统计数据。
     * @return
     * @throws ClientException
     */
    public Object queryDeviceStatistics(String productKey) {
        QueryDeviceStatisticsRequest request = new QueryDeviceStatisticsRequest();
        if (StringUtils.isNoneBlank(productKey)) {
            request.setProductKey(productKey);
        }
        QueryDeviceStatisticsResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
