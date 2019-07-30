package com.thunk.iot.controller;

import com.thunk.iot.dto.BaseResult;
import com.thunk.iot.util.system.GetSystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/13 21:37
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@RestController
@RequestMapping("/system")
@Slf4j
public class SystemInfoController {

    @Autowired
    private GetSystemInfo getSystemInfo;

    @RequiresPermissions("system:select")
    @GetMapping
    public BaseResult getSystemInfo() throws SigarException, UnknownHostException {
        return BaseResult.ok(getSystemInfo.getSystemInfo());
    }

    @GetMapping("/test")
    public String test(){
        String property = System.getProperty("java.library.path");
        log.info("【java.library.path路径】{}",property);
        return property;
    }
}



