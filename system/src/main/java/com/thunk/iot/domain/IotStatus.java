package com.thunk.iot.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: Wemos设备状态实体
 * @Date:     2019/5/8 16:14
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@NoArgsConstructor
@Data
public class IotStatus {

    /**
     * lastTime : 2019-05-08 15:59:30.652
     * utcLastTime : 2019-05-08T07:59:30.652Z
     * clientIp : 118.123.108.19
     * utcTime : 2019-05-08T07:59:30.826Z
     * time : 2019-05-08 15:59:30.826
     * productKey : a1GnhkZH96h
     * deviceName : Wemos
     * status : offline
     */

    private String lastTime;
    private String utcLastTime;
    private String clientIp;
    private String utcTime;
    private String time;
    private String productKey;
    private String deviceName;
    private String status;
}
