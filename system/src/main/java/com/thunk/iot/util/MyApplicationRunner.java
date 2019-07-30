package com.thunk.iot.util;

import com.thunk.iot.aliyun.iot.service.servicerss.AliyunIotInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 应用启动后自动执行类
 * @Date:     2019/5/8 13:42
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@Slf4j
@Order(value = 1)
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private AliyunIotInit aliyunIotInit;

    @Override
    public void run(ApplicationArguments args) {
        aliyunIotInit.subService();
    }
}
