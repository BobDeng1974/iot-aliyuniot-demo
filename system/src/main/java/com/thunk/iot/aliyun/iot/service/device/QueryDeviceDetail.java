package com.thunk.iot.aliyun.iot.service.device;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailRequest;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:52
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class QueryDeviceDetail {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 查询指定设备的详细信息
     * @param iotId 设备ID
     * @return
     * @throws ClientException
     */
    public Object queryDeviceDetail(String iotId) {
        QueryDeviceDetailRequest request = new QueryDeviceDetailRequest();
        request.setIotId(iotId);
        QueryDeviceDetailResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
