package com.thunk.iot.aliyun.iot.service.device;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.RegisterDeviceRequest;
import com.aliyuncs.iot.model.v20180120.RegisterDeviceResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaolei
 * @Version 1.0.0
 * @Description:
 * @Date: 2019/5/9 10:35
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class RegisterDevice {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 在指定产品下注册设备
     *
     * @param productKey 待注册设备所隶属的产品的Key
     * @param deviceName 设备命名
     * @param nickname   设备设置备注名称
     * @return
     * @throws ClientException
     */
    public Object registerDevice(String productKey, String deviceName, String nickname) {
        RegisterDeviceRequest request = new RegisterDeviceRequest();
        if (StringUtils.isNoneBlank(deviceName)) {
            request.setDeviceName(deviceName);
        }
        if (StringUtils.isNoneBlank(nickname)) {
            request.setNickname(nickname);
        }
        request.setProductKey(productKey);
        RegisterDeviceResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
