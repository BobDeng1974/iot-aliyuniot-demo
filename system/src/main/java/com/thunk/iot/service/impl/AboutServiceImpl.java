package com.thunk.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thunk.iot.dao.AboutMapper;
import com.thunk.iot.domain.About;
import com.thunk.iot.service.AboutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AboutServiceImpl implements AboutService {

    @Resource
    private AboutMapper aboutMapper;

    @Override
    public int createAbout(About about) {
        return aboutMapper.insert(about);
    }

    @Override
    public int updateAbout(About about) {
        return aboutMapper.updateByPrimaryKey(about);
    }

    @Override
    public int deleteAboutById(Integer id) {
        return aboutMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo queryAbout(About about, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<About> pageInfo = new PageInfo<>(aboutMapper.select(about));
        return pageInfo;
    }
}

