package com.thunk.iot.service.impl;

import com.thunk.iot.dao.DeviceDataMapper;
import com.thunk.iot.domain.DeviceData;
import com.thunk.iot.service.IotService;
import org.springframework.stereotype.Service;

@Service
public class IotServiceImpl extends BaseServiceImpl<DeviceData, DeviceDataMapper> implements IotService<DeviceData> {

}
