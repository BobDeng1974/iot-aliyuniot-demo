package com.thunk.iot.controller.aliyun;

import com.aliyuncs.iot.model.v20180120.RegisterDeviceRequest;
import com.thunk.iot.aliyun.iot.service.device.*;
import com.thunk.iot.dto.BaseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 设备管理
 * @Date:     2019/5/9 16:50
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private QueryDevice queryDevice;

    @Autowired
    private QueryDeviceDetail queryDeviceDetail;

    @Autowired
    private GetDeviceStatus getDeviceStatus;

    @Autowired
    private RegisterDevice registerDevice;

    @Autowired
    private DeleteDevice deleteDevice;

    @Autowired
    private QueryDeviceStatistics queryDeviceStatistics;

    /**
     * 查询某产品下的设备
     * @param productKey
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequiresPermissions("device:list")
    @GetMapping(value = "/product/{productKey}/{currentPage}/{pageSize}", produces = "application/json;charset=UTF-8")
    public Object queryDevice(@PathVariable("productKey") String productKey,
                              @PathVariable("currentPage") Integer currentPage,
                              @PathVariable("pageSize") Integer pageSize){
        return queryDevice.queryDevice(productKey, pageSize, currentPage);
    }

    /**
     * 查询所有设备（自定义）
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequiresPermissions("device:allList")
    @GetMapping(value = "/all/{currentPage}/{pageSize}", produces = "application/json;charset=UTF-8")
    public BaseResult queryAllDevice(@PathVariable("currentPage") Integer currentPage,
                                     @PathVariable("pageSize") Integer pageSize){
        List queryAllDevice = queryDevice.queryAllDevice(pageSize, currentPage);
        return BaseResult.ok(queryAllDevice);
    }

    /**
     * 查看指定设备的运行状态
     * @param iotId
     * @return
     */
    @RequiresPermissions("device:status")
    @GetMapping(value = "/single/{iotId}", produces = "application/json;charset=UTF-8")
    public Object getDeviceStatus(@PathVariable("iotId") String iotId){
        return getDeviceStatus.getDeviceStatus(iotId);
    }

    /**
     * 查询所有设备状态
     * @return
     */
    @RequiresPermissions("device:allStatus")
    @GetMapping(value = "/allStatus", produces = "application/json;charset=UTF-8")
    public Object queryDeviceStatistics(@RequestParam(value = "productKey", required = false) String productKey){
        System.out.println(productKey);
        return queryDeviceStatistics.queryDeviceStatistics(productKey);
    }

    /**
     * 查询设备详情
     * @param iotId
     * @return
     */
    @RequiresPermissions("device:detail")
    @GetMapping(value = "/detail/{iotId}", produces = "application/json;charset=UTF-8")
    public Object queryDeviceDetail(@PathVariable("iotId") String iotId){
        return queryDeviceDetail.queryDeviceDetail(iotId);
    }

    /**
     * 添加设备
     * @param request
     * @return
     */
    @RequiresPermissions("device:add")
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Object registerDevice(@RequestBody RegisterDeviceRequest request){
        return registerDevice.registerDevice(request.getProductKey(), request.getDeviceName(), request.getNickname());
    }

    /**
     * 删除设备
     * @param iotId
     * @return
     */
    @RequiresPermissions("device:delete")
    @DeleteMapping(value = "/delete/{iotId}", produces = "application/json;charset=UTF-8")
    public Object deleteDevice(@PathVariable("iotId") String iotId){
        return deleteDevice.deleteDevice(iotId);
    }
}
