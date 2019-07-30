package com.thunk.iot.aliyun.iot.service.group;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 分组管理
 * @Date:     2019/5/9 11:19
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Component
public class DeviceGroup {

    @Autowired
    private DefaultAcsClient acsClient;

    /**
     * 新建分组
     * @param groupName 分组名称
     * @param groupDesc 分组描述
     * @return
     * @throws ClientException
     */
    public Object createDeviceGroup(String groupName, String groupDesc) {
        CreateDeviceGroupRequest request = new CreateDeviceGroupRequest();
        request.setGroupName(groupName);
        request.setGroupDesc(groupDesc);
        CreateDeviceGroupResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 修改分组信息
     * @param groupId 分组ID
     * @param groupDesc 修改后的分组描述。长度限制为100字符（一个中文汉字占一个字符）。
     * @return
     * @throws ClientException
     */
    public Object updateDeviceGroup(String groupId, String groupDesc) {
        UpdateDeviceGroupRequest request = new UpdateDeviceGroupRequest();
        request.setGroupId(groupId);
        request.setGroupDesc(groupDesc);
        UpdateDeviceGroupResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);

    }

    /**
     * 删除指定分组
     * @param groupId 分组ID
     * @return
     * @throws ClientException
     */
    public Object deleteDeviceGroup(String groupId) {
        DeleteDeviceGroupRequest request = new DeleteDeviceGroupRequest();
        request.setGroupId(groupId);
        DeleteDeviceGroupResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 添加设备到某一分组（可批量添加设备）
     * @param groupId 分组ID
     * @param devices 要添加到分组的设备信息
     * @return
     * @throws ClientException
     */
    public Object batchAddDeviceGroupRelations(String groupId,
                                               List<BatchAddDeviceGroupRelationsRequest.Device> devices) {
        BatchAddDeviceGroupRelationsRequest request = new BatchAddDeviceGroupRelationsRequest();
        request.setGroupId(groupId);
        request.setDevices(devices);
        BatchAddDeviceGroupRelationsResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 批量删除指定分组中的设备
     * @param groupId 分组ID
     * @param devices 要从分组中删除的设备信息
     * @return
     * @throws ClientException
     */
    public Object batchDeleteDeviceGroupRelations(String groupId,
                                                  List<BatchDeleteDeviceGroupRelationsRequest.Device> devices) {
        BatchDeleteDeviceGroupRelationsRequest request = new BatchDeleteDeviceGroupRelationsRequest();
        request.setGroupId(groupId);
        request.setDevices(devices);
        BatchDeleteDeviceGroupRelationsResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 查询分组详情
     * @param groupId 分组ID
     * @return
     * @throws ClientException
     */
    public Object queryDeviceGroupInfo(String groupId) {
        QueryDeviceGroupInfoRequest request = new QueryDeviceGroupInfoRequest();
        request.setGroupId(groupId);
        QueryDeviceGroupInfoResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 分页查询分组列表
     * @param pageSize 每页记录数
     * @param currentPage 指定从返回结果中的第几页开始显示
     * @return
     * @throws ClientException
     */
    public Object queryDeviceGroupList(Integer pageSize, Integer currentPage) {
        QueryDeviceGroupListRequest request = new QueryDeviceGroupListRequest();
        request.setCurrentPage(currentPage);
        request.setPageSize(pageSize);
        QueryDeviceGroupListResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 查询分组中的设备列表
     * @param groupId 分组ID
     * @param pageSize 指定显示查询结果中的第几页。默认值为1。
     * @param currentPage 指定返回结果中，每页显示的设备数量。默认值为10。
     * @return
     * @throws ClientException
     */
    public Object queryDeviceListByDeviceGroup(String groupId, Integer pageSize, Integer currentPage) {
        QueryDeviceListByDeviceGroupRequest request = new QueryDeviceListByDeviceGroupRequest();
        request.setGroupId(groupId);
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        QueryDeviceListByDeviceGroupResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }

    /**
     * 查询某一设备所在的分组列表
     * @param productKey
     * @param deviceName
     * @return
     */
    public  Object queryDeviceGroupByDevice(String productKey, String deviceName){
        QueryDeviceGroupByDeviceRequest request = new QueryDeviceGroupByDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        QueryDeviceGroupByDeviceResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(response);
    }
}
