package com.thunk.iot.aliyun.iot.service.device;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.DeleteDeviceRequest;
import com.aliyuncs.iot.model.v20180120.DeleteDeviceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/9 10:56
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class DeleteDevice {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 删除指定设备
     * @param iotId 设备ID
     * @return
     * @throws ClientException
     */
    public Object deleteDevice(String iotId) {
        DeleteDeviceRequest request = new DeleteDeviceRequest();
        request.setIotId(iotId);
        DeleteDeviceResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
