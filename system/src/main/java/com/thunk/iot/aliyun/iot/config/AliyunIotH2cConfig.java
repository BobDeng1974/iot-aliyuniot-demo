package com.thunk.iot.aliyun.iot.config;

import lombok.Data;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 服务端订阅配置
 * @Date:     2019/5/8 11:23
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@Data
public class AliyunIotH2cConfig {
    private String accessKey;
    private String accessSecret;
    private String regionId;
    private String uid;
    private String endPoint;
}
