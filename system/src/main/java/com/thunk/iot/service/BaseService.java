package com.thunk.iot.service;

import com.github.pagehelper.PageInfo;

public interface BaseService<T> {
    int insert(T t);

    int delete(T t);

    int update(T t);

    T selectOne(T t);

    PageInfo<T> page(int pageNum, int pageSize, T t);
}
