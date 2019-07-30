package com.thunk.iot.controller.aliyun;

import com.aliyuncs.iot.model.v20180120.*;
import com.thunk.iot.aliyun.iot.service.group.DeviceGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 设备分组
 * @Date:     2019/5/9 19:31
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@RestController
@RequestMapping("/group")
public class DeviceGroupController {

    @Autowired
    private DeviceGroup deviceGroup;

    /**
     * 创建分组
     * @param request
     * @return
     */
    @RequiresPermissions("group:add")
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Object createGroup(@RequestBody CreateDeviceGroupRequest request){
        return deviceGroup.createDeviceGroup(request.getGroupName(), request.getGroupDesc());
    }

    /**
     * 更新分组信息
     * @param request
     * @return
     */
    @RequiresPermissions("group:update")
    @PutMapping(value = "/{groupId}", produces = "application/json;charset=UTF-8")
    public Object updateGroup(@PathVariable("groupId") String groupId, @RequestBody UpdateDeviceGroupRequest request){
        return deviceGroup.updateDeviceGroup(groupId, request.getGroupDesc());
    }

    /**
     * 删除分组
     * @param groupId
     * @return
     */
    @RequiresPermissions("group:delete")
    @DeleteMapping(value = "/{groupId}", produces = "application/json;charset=UTF-8")
    public Object deleteGroup(@PathVariable("groupId") String groupId){
        return deviceGroup.deleteDeviceGroup(groupId);
    }

    /**
     * 批量添加设备到分组
     * @param request
     * @return
     */
    @RequiresPermissions("group:batchAddDevice")
    @PostMapping(value = "/addDevice/{groupId}", produces = "application/json;charset=UTF-8")
    public Object AddDeviceToGroup(@PathVariable("groupId") String groupId,
                                   @RequestBody List<BatchAddDeviceGroupRelationsRequest.Device> request){
        return deviceGroup.batchAddDeviceGroupRelations(groupId, request);
    }

    /**
     * 批量从分组删除设备
     * @param request
     * @return
     */
    @RequiresPermissions("group:batchDeleteDevice")
    @DeleteMapping(value = "/deleteDevice/{groupId}", produces = "application/json;charset=UTF-8")
    public Object deleteDeviceToGroup(@PathVariable("groupId") String groupId,
                                      @RequestBody List<BatchDeleteDeviceGroupRelationsRequest.Device> request){
        return deviceGroup.batchDeleteDeviceGroupRelations(groupId, request);
    }

    /**
     * 查询分组详情
     * @param groupId
     * @return
     */
    @RequiresPermissions("group:detail")
    @GetMapping(value = "/{groupId}", produces = "application/json;charset=UTF-8")
    public Object queryDeviceGroupInfo(@PathVariable("groupId") String groupId){
        return deviceGroup.queryDeviceGroupInfo(groupId);
    }

    /**
     * 分页查询分组列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequiresPermissions("group:list")
    @GetMapping(value = "/list/{currentPage}/{pageSize}", produces = "application/json;charset=UTF-8")
    public Object quertyDeviceGroupList(@PathVariable("currentPage") Integer currentPage,
                                        @PathVariable("pageSize") Integer pageSize){
        return deviceGroup.queryDeviceGroupList(pageSize, currentPage);
    }

    /**
     * 查询分组中的设备列表
     * @param currentPage
     * @param pageSize
     * @param groupId
     * @return
     */
    @RequiresPermissions("group:deviceList")
    @GetMapping(value = "/queryDevice/{groupId}/{currentPage}/{pageSize}", produces = "application/json;charset=UTF-8")
    public Object queryDeviceListByGroup(@PathVariable("currentPage") Integer currentPage,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("groupId") String groupId){
        return deviceGroup.queryDeviceListByDeviceGroup(groupId, pageSize, currentPage);
    }

    /**
     * 查询某一设备所在的分组列表
     * @param request
     * @return
     */
    @RequiresPermissions("group:deviceByGroup")
    @GetMapping("/deviceByGroup")
    public Object queryDeviceGroupByDevice(@RequestBody QueryDeviceGroupByDeviceRequest request){
        return deviceGroup.queryDeviceGroupByDevice(request.getProductKey(), request.getDeviceName());
    }
}
