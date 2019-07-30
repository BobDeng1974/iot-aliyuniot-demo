package com.thunk.iot.aliyun.iot.service.servicerss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.thunk.iot.controller.aliyun.WebSocketController;
import com.thunk.iot.domain.DeviceData;
import com.thunk.iot.domain.IotData;
import com.thunk.iot.domain.IotStatus;
import com.thunk.iot.service.IotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 阿里云物联网平台服务端订阅
 * @Date:     2019/5/8 10:58
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@Slf4j
@Component
public class AliyunIotInit {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private WebSocketController webSocketController;

    public void subService() {
        messageClient.connect(messageToken -> {
            Message message = messageToken.getMessage();
            int status = handleData(message.getTopic(), new String(message.getPayload()));

            return status == 1 ? MessageCallback.Action.CommitSuccess : MessageCallback.Action.CommitFailure;
        });
    }


    @Autowired
    private IotService iotService;

    /**
     * 处理订阅到的消息
     * @param topic Topic
     * @param payload data
     */
    public int handleData(String topic, String payload){
        switch (topic){
            case "/as/mqtt/status/a1GnhkZH96h/Wemos":
                return checkStatus(payload);

            case "/a1GnhkZH96h/Wemos/thing/event/property/post":
                return saveData(payload);

            case "/as/mqtt/status/a1kEF08nQuD/NodeMCU":
                return checkStatus(payload);

            case "/a1kEF08nQuD/NodeMCU/thing/event/property/post":
                return saveData(payload);

            default:
                log.warn("没有匹配的topic，当前Topic：{}", topic);
                return 1;
        }
    }


    /**
     * 处理设备数据消息
     * @param payload 数据
     * @return
     */
    public int saveData(String payload){
        IotData iotData = JSON.toJavaObject(JSONObject.parseObject(payload), IotData.class);
        DeviceData deviceData = new DeviceData();
        deviceData.setIotId(iotData.getIotId());
        deviceData.setData(iotData.getItems());
        deviceData.setCreateTime(new Date());

        webSocketController.sendTopicMessage(iotData.getIotId(), JSONObject.parseObject(payload));

        return iotService.insert(deviceData);
    }

    /**
     * 处理设备状态消息
     * @param payload 状态
     */
    public int checkStatus(String payload){
        IotStatus iotStatus = JSON.toJavaObject(JSONObject.parseObject(payload), IotStatus.class);
        log.warn("DeviceName: {}, Status: {}, Time: {}, ClientIP: {}", iotStatus.getDeviceName(), iotStatus.getStatus(), iotStatus.getTime(),iotStatus.getClientIp());
        return 1;
    }
}
