package com.thunk.iot.util.system;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thunk.iot.controller.aliyun.WebSocketController;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: zhaolei
 *
 * @Version: 1.0.0
 * @Description:
 * @Date 2019/5/29 12:25
 * Copyright © 2018 ~ 2019 Yunkont.com
 */

@Component
@Slf4j
public class GetSystemInfo {

    public Object getSystemInfo() throws UnknownHostException, SigarException {
        InetAddress addr = InetAddress.getLocalHost();
        Properties props = System.getProperties();

        Sigar sigar = new Sigar();


        Map systemInfo = new HashMap<String, Object>(16);
        systemInfo.put("IP", addr.getHostAddress());
        systemInfo.put("name", addr.getHostName());
        systemInfo.put("osName", props.getProperty("os.name"));
        systemInfo.put("osArch", props.getProperty("os.arch"));
        systemInfo.put("osVersion", props.getProperty("os.version"));

        return systemInfo;
    }

    @Autowired
    private WebSocketController webSocketController;

    /**
     * 发送系统信息
     * @throws SigarException
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void getSystemMemory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        int memTotal = Math.round(mem.getTotal() / 1024 / 1024);
        int memUsed = Math.round(mem.getUsed() / 1024 / 1024);
        double memRate = new BigDecimal(((float)memUsed/memTotal)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        Swap swap = sigar.getSwap();
        int swapTotal = Math.round(swap.getTotal() / 1024 / 1024);
        int swapUsed = Math.round(swap.getUsed() / 1024 / 1024);
//        double swapRate = new BigDecimal(((float)swapUsed/swapTotal)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        double swapRate = Double.valueOf(String.format("%.2f", (swapUsed/swapTotal)*100));

        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuPerc[] cpuList = sigar.getCpuPercList();
        int cpuTotal = infos.length;
        double sum = 0;
        for (int i = 0; i < infos.length; i++) {
            sum += Double.valueOf(CpuPerc.format(cpuList[i].getCombined()).substring(0, CpuPerc.format(cpuList[i].getCombined()).length()-1));
        }
        double cpuUsed = Double.valueOf(String.format("%.2f",sum / infos.length));

        FileSystem[] fslist = sigar.getFileSystemList();

        int sdAll = 0;
        int sdUse = 0;

        for (int i = 0; i < fslist.length; i++) {

            FileSystem fs = fslist[i];
            FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());

            switch (fs.getType()) {
                /**
                 * @TYPE_LOCAL_DISK : 本地硬盘
                 */
                case 2:
                    sdAll+=usage.getTotal();
                    sdUse+=usage.getUsed();
                    break;
                default:
                    break;
            }
        }

        int sdTotal = Math.round(sdAll / 1024 / 1024);
        int sdUsed = Math.round(sdUse / 1024 / 1024);
        double sdRate = new BigDecimal(((float)sdUsed/sdTotal)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        Map system = new HashMap<String, Object>(16);
        system.put("memTotal", memTotal);
        system.put("memUsed", memUsed);
        system.put("swapTotal", swapTotal);
        system.put("swapUsed", swapUsed);
        system.put("cpuTotal", cpuTotal);
        system.put("cpuUsed", cpuUsed);
        system.put("sdTotal", sdTotal);
        system.put("sdUsed", sdUsed);
        system.put("memRate", memRate);
//        system.put("swapRate", swapRate);
        system.put("sdRate", sdRate);

        webSocketController.sendSystemInfo(JSONObject.parseObject(JSON.toJSONString(system)));
    }
}
