package com.xy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.domain.House;
import com.xy.domain.Product;
import com.xy.domain.Seq;
import com.xy.domain.Staff;
import com.xy.service.BaseDataService;
import com.xy.service.HouseService;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private HouseService houseService;

    // 增加产品
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    private void addProduct(@RequestBody Product product) throws Exception {
        baseDataService.addProduct(product);
    }

    // 删除产品
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    private void delProduct(@PathVariable("id") int id) throws Exception {
        baseDataService.delProduct(id);
    }

    // 获取产品 带分页
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    private List<Product> getProductPage(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return baseDataService.getAllProduct(page, size);
    }

    // 添加工序
    @RequestMapping(value = "/product/{id}/seq", method = RequestMethod.POST)
    private void addSeq(@RequestBody Seq seq, @PathVariable("id") int id) throws Exception {
        baseDataService.addSeqByProductId(id, seq);
    }

    // 查询工序
    @RequestMapping(value = "/product/{id}/seq", method = RequestMethod.GET)
    private Set<Seq> getSeq(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
       return baseDataService.getSeqListByProductId(id);
    }

    // 删除工序
    @RequestMapping(value = "/product/{id}/seq/{idseq}", method = RequestMethod.DELETE)
    private void delSeq(@PathVariable("idSeq") int id) throws Exception {
        baseDataService.delSeqById(id);
    }

    // 给工序添加默认员工
    @RequestMapping(value = "/seq/{id}/staff", method = RequestMethod.POST)
    private void addStaff(@RequestBody Staff staff, @PathVariable("id") int id) throws Exception {
        baseDataService.addStaffBySeqId(id, staff);
    }

    // 获得工序的默认员工
    @RequestMapping(value = "/seq/{id}/staff", method = RequestMethod.GET)
    private Set<Staff> getDefaultStaff(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return baseDataService.getStaffBySeqId(id);
    }

    // 添加员工
    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    private void addStaff(@RequestBody Staff staff) throws Exception {
        staffService.addStaff(staff);
    }

    // 获取员工
    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    private List<Staff> getStaff(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("status") int status) throws Exception{
        return staffService.getStaffListByStatus(page, size, status);
    }
    // 添加仓库
    @RequestMapping(value = "/house", method = RequestMethod.POST)
    public void addHouse(@RequestBody House house) throws Exception {
        houseService.addHouse(house);
    }

    // 获取仓库 分页
    @RequestMapping(value = "/house", method = RequestMethod.GET)
    public Page<House> getHouses(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return houseService.getPageHouse(page, size);
    }
}
