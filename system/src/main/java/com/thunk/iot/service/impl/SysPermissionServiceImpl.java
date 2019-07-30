package com.thunk.iot.service.impl;

import com.thunk.iot.dao.SysPermissionMapper;
import com.thunk.iot.domain.SysPermission;
import com.thunk.iot.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionMapper> implements SysPermissionService<SysPermission> {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int updateByPermissionId(SysPermission sysPermission) {
        return sysPermissionMapper.updateByPermissionId(sysPermission);
    }
}
