package com.xy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.domain.Product;
import com.xy.domain.Seq;
import com.xy.domain.Staff;
import com.xy.service.BaseDataService;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/data")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private StaffService staffService;

    // 增加产品
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    private void addProduct(@RequestBody Product product) throws Exception {
        baseDataService.addProduct(product);
    }

    // 删除产品
    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    private void delProduct(@RequestBody JSONObject obj) throws Exception {
        baseDataService.delProduct(obj.getIntValue("id"));
    }

    // 获取产品 带分页
    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    private Page<Product> getProductPage(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return baseDataService.getAllProduct(page, size);
    }

    // 获取产品 列表
    @RequestMapping(value = "/getProductsList", method = RequestMethod.GET)
    private List<Product> getProductList() throws Exception {
        return baseDataService.getAllProduct();
    }

    // 添加工序
    @RequestMapping(value = "/addSeq/{idProduct}", method = RequestMethod.POST)
    private void addSeq(@RequestBody Seq seq, @PathVariable("idProduct") int id) throws Exception {
        baseDataService.addSeqByProductId(id, seq);
    }

    // 查询工序
    @RequestMapping(value = "/getSeq/{idProduct}", method = RequestMethod.GET)
    private Set<Seq> getSeq(@PathVariable("idProduct") int id) throws Exception {
       return baseDataService.getSeqListByProductId(id);
    }

    // 删除工序
    @RequestMapping(value = "/delSeq", method = RequestMethod.POST)
    private void delSeq(@RequestBody JSONObject obj) throws Exception {
        baseDataService.delSeqById(obj.getIntValue("id"));
    }

    // 给工序添加默认员工
    @RequestMapping(value = "/addStaff/{idSeq}", method = RequestMethod.POST)
    private void addStaff(@RequestBody Staff staff, @PathVariable("idSeq") int id) throws Exception {
        baseDataService.addStaffBySeqId(id, staff);
    }

    // 获得工序的默认员工
    @RequestMapping(value = "/getStaff/{idSeq}", method = RequestMethod.GET)
    private Set<Staff> getStaff(@PathVariable("idSeq") int id) throws Exception {
        return baseDataService.getStaffBySeqId(id);
    }

    // 添加员工
    @RequestMapping(value = "/addStaff", method = RequestMethod.POST)
    private void addStaff(@RequestBody Staff staff) throws Exception {
        staffService.addStaff(staff);
    }
}
