package com.thunk.iot.dao;

import com.thunk.iot.domain.SysPermission;
import tk.mybatis.mapper.MyMapper;

public interface SysPermissionMapper extends MyMapper<SysPermission> {

    /**
     * 根据ID更新权限
     * @param sysPermission
     * @return
     */
    int updateByPermissionId(SysPermission sysPermission);
}