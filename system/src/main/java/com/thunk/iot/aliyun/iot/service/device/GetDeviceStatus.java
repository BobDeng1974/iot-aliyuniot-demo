package com.thunk.iot.aliyun.iot.service.device;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.GetDeviceStatusRequest;
import com.aliyuncs.iot.model.v20180120.GetDeviceStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 11:00
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class GetDeviceStatus {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 查看指定设备的运行状态
     * @param iotId
     * @return 设备ID
     * @throws ClientException
     */
    public Object getDeviceStatus(String iotId) {
        GetDeviceStatusRequest request = new GetDeviceStatusRequest();
        request.setIotId(iotId);
        GetDeviceStatusResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
