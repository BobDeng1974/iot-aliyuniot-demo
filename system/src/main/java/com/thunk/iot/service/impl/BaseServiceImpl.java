package com.thunk.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thunk.iot.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.MyMapper;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/11 13:07
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseServiceImpl<T, D extends MyMapper<T>> implements BaseService<T> {

    @Autowired
    private D dao;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insert(T t) {
        return dao.insert(t);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delete(T t) {
        return dao.delete(t);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(T t) {
        return dao.updateByPrimaryKeySelective(t);
//        return dao.updateByPrimaryKey(t);
    }

    @Override
    public T selectOne(T t) {
        return dao.selectOne(t);
    }

    @Override
    public PageInfo<T> page(int pageNum, int pageSize, T t) {
//        PageHelper pageHelper = new PageHelper();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<T> pageInfo = new PageInfo<>(dao.select(t));
        return pageInfo;
    }
}
