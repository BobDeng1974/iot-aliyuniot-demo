package com.thunk.iot.dao;

import com.thunk.iot.domain.DeviceData;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.MyMapper;

@Mapper
public interface DeviceDataMapper extends MyMapper<DeviceData> {
}