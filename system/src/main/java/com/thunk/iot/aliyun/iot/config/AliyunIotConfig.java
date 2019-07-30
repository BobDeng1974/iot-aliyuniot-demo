package com.thunk.iot.aliyun.iot.config;

import lombok.Data;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 云端API对接配置
 * @Date:     2019/5/5 18:49
 * Copyright (C), 2016-2018, EasyLinker V3
 */
@Data
public class AliyunIotConfig {
    private String accessKey;
    private String accessSecret;
    private String endpointName;
    private String regionId;
    private String product;
    private String domain;
}

