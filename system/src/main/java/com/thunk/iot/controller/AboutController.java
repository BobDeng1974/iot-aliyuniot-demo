package com.thunk.iot.controller;


import com.github.pagehelper.PageInfo;
import com.thunk.iot.domain.About;
import com.thunk.iot.dto.BaseResult;
import com.thunk.iot.dto.ResultEnum;
import com.thunk.iot.service.AboutService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @RequiresPermissions("about:add")
    @PostMapping
    public BaseResult createAbout(@RequestBody About about){
        about.setCreateTime(new Date());
        about.setUpdateTime(new Date());
        about.setStatus(1);
        if (aboutService.createAbout(about) == 1) {
            return BaseResult.ok();
        }
        return BaseResult.notOk(ResultEnum.SaveError.getCode(), ResultEnum.SaveError.getMessage());
    }

    @RequiresPermissions("about:update")
    @PutMapping("/{id}")
    public BaseResult updateAbout(@PathVariable("id") Integer id,
                                  @RequestBody About about){
        about.setUpdateTime(new Date());
        about.setId(id);
        if (aboutService.updateAbout(about) == 1){
            return BaseResult.ok();
        }
        return BaseResult.notOk(ResultEnum.SaveError.getCode(), ResultEnum.SaveError.getMessage());
    }

    @RequiresPermissions("about:delete")
    @DeleteMapping("/{id}")
    public BaseResult deleteAbout(@PathVariable("id") Integer id){
        if (aboutService.deleteAboutById(id) == 1){
            return BaseResult.ok();
        }
        return BaseResult.notOk(ResultEnum.DeleteError.getCode(), ResultEnum.DeleteError.getMessage());
    }


    @RequiresPermissions("about:list")
    @GetMapping("/{pageNum}/{pageSize}")
    public BaseResult queryAbout(@PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("pageSize") Integer pageSize,
                                 @RequestBody(required = false) About about){
        PageInfo page = aboutService.queryAbout(about, pageNum, pageSize);
        List<About> list = page.getList();
        BaseResult.Cursor cursor = new BaseResult.Cursor();
        cursor.setTotal((int) page.getTotal());
        cursor.setOffset(page.getPageNum());
        cursor.setLimit(page.getPageSize());
        cursor.setPages(page.getPages());
        cursor.setFirstPage(page.isIsFirstPage());
        cursor.setLastPage(page.isIsLastPage());
        cursor.setPreviousPage(page.isHasPreviousPage());
        cursor.setNextPage(page.isHasNextPage());
        return BaseResult.ok(list, cursor);
    }
}
