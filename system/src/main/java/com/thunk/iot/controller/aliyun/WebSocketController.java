package com.thunk.iot.controller.aliyun;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: WebSocketController
 * @Date:     2019/5/10 10:47
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Controller
@Component
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 广播消息（推送服务端订阅数据到前端）
     * @param data
     */
    public void sendTopicMessage(String iotID, JSONObject data){
        messagingTemplate.convertAndSend("/topic/data/"+iotID, data);
    }

    /**
     * 广播消息（推送系统内存信息）
     * @param data
     */
    public void sendSystemInfo(JSONObject data){
        messagingTemplate.convertAndSend("/topic/system", data);
    }

}
