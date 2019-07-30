package com.thunk.iot.service;

import com.github.pagehelper.PageInfo;
import com.thunk.iot.domain.About;

public interface AboutService {

    int createAbout(About about);

    int updateAbout(About about);

    int deleteAboutById(Integer id);

    PageInfo queryAbout(About about, Integer pageNum, Integer pageSize);

}

