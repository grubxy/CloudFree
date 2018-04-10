package com.xy.controller;

import com.alibaba.fastjson.JSON;
import com.xy.domain.Product;
import com.xy.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/data")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    // 增加产品
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    private void addProduct(@RequestBody Product product) throws Exception {
        baseDataService.addProduct(product);
    }

    // 删除产品
    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    private void delProduct(@RequestBody int id) throws Exception {
        baseDataService.delProduct(id);
    }

    // 产品列表
    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    private String getProductList(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        if (page == 0 && size == 0) {
            return JSON.toJSONString(baseDataService.getAllProduct());
        } else {
            return JSON.toJSONString(baseDataService.getAllProduct(page, size));
        }
    }
}
