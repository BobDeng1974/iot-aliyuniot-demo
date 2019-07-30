package com.thunk.iot.controller.aliyun;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.CreateProductRequest;
import com.aliyuncs.iot.model.v20180120.UpdateProductRequest;
import com.thunk.iot.aliyun.iot.service.product.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 产品管理
 * @Date:     2019/5/9 12:37
 * Copyright (C), 2016-2019, EasyLinker V3
 */

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private QueryProductList queryProductList;

    @Autowired
    private QueryProduct queryProduct;

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private UpdateProduct updateProduct;

    @Autowired
    private DeleteProduct deleteProduct;

    /**
     * 查询产品列表
     * @param currentPage
     * @param pageSize
     * @return
     * @throws ClientException
     */
    @RequiresPermissions("product:list")
    @GetMapping(value = "/list/{currentPage}/{pageSize}", produces = "application/json;charset=UTF-8")
    public Object queryProductList(@PathVariable(value = "currentPage") Integer currentPage,
                                   @PathVariable(value = "pageSize") Integer pageSize) {
        return queryProductList.queryProductList(currentPage, pageSize);
    }

    /**
     * 查询产品详情
     * @param productKey
     * @return
     */
    @RequiresPermissions("product:detail")
    @GetMapping(value = "/detail/{productKey}", produces = "application/json;charset=UTF-8")
    public Object queryProduct(@PathVariable("productKey") String productKey){
        return queryProduct.queryProduct(productKey);
    }

    /**
     * 创建产品
     * @param request
     * @return
     */
    @RequiresPermissions("product:add")
    @PostMapping(produces = "application/json;charset=UTF-8")
    public Object createProduct(@RequestBody CreateProductRequest request){
        return createProduct.createProduct(request.getProductName().trim(), request.getNodeType(), request.getDataFormat(), request.getDescription(), request.getNetType());
    }

    /**
     * 更新设备信息
     * @param request
     * @return
     */
    @RequiresPermissions("product:update")
    @PutMapping(value = "/{productKey}", produces = "application/json;charset=UTF-8")
    public Object updateProduct(@PathVariable("productKey") String productKey, @RequestBody UpdateProductRequest request){
        return updateProduct.updateProduct(productKey, request.getProductName(), request.getDescription());
    }

    /**
     * 删除产品
     * @param productKey
     * @return
     */
    @RequiresPermissions("product:delete")
    @DeleteMapping(value = "/{productKey}", produces = "application/json;charset=UTF-8")
    public Object deleteProduct(@PathVariable("productKey") String productKey){
        return deleteProduct.deleteProduct(productKey);
    }
}
