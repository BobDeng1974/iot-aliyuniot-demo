package com.thunk.iot.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "device_data")
public class DeviceData {
    /**
     * 序列号
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 设备ID
     */
    @Column(name = "iot_id")
    private String iotId;

    /**
     * 入库时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 设备数据（JSON）
     */
    private String data;

    /**
     * 获取序列号
     *
     * @return id - 序列号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序列号
     *
     * @param id 序列号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取设备ID
     *
     * @return iot_id - 设备ID
     */
    public String getIotId() {
        return iotId;
    }

    /**
     * 设置设备ID
     *
     * @param iotId 设备ID
     */
    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    /**
     * 获取入库时间
     *
     * @return create_time - 入库时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置入库时间
     *
     * @param createTime 入库时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取设备数据（JSON）
     *
     * @return data - 设备数据（JSON）
     */
    public String getData() {
        return data;
    }

    /**
     * 设置设备数据（JSON）
     *
     * @param data 设备数据（JSON）
     */
    public void setData(String data) {
        this.data = data;
    }
}