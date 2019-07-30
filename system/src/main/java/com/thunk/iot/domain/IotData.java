package com.thunk.iot.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: Wemos数据实体
 * @Date:     2019/5/8 16:14
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@NoArgsConstructor
@Data
public class IotData {

    /**
     * deviceType : Wristband
     * iotId : vbRnWfpZtlEigeCMAVSV000100
     * productKey : a1GnhkZH96h
     * gmtCreate : 1557291884963
     * deviceName : Wemos
     * items : {"LightSwitch":{"time":1557291884971,"value":0},"AnimalTemperature":{"time":1557291884971,"value":0},"mhumi":{"time":1557291884971,"value":931}}
     */

    private String deviceType;
    private String iotId;
    private String productKey;
    private long gmtCreate;
    private String deviceName;
    private String items;
}
