package com.thunk.iot.service;

public interface SysPermissionService<T> extends BaseService<T> {

    int updateByPermissionId(T t);
}
