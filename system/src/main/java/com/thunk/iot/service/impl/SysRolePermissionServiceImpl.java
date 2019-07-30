package com.thunk.iot.service.impl;

import com.thunk.iot.dao.SysRolePermissionMapper;
import com.thunk.iot.domain.SysRolePermission;
import com.thunk.iot.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermission, SysRolePermissionMapper> implements SysRolePermissionService<SysRolePermission> {
}
