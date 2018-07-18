package com.xy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xy.domain.*;
import com.xy.service.BaseDataService;
import com.xy.service.HouseService;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public void addProduct(@RequestBody Product product) throws Exception {
        baseDataService.addProduct(product);
    }

    // 删除产品
    @PreAuthorize("hasRole('SYSTEM')")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void delProduct(@PathVariable("id") int id) throws Exception {
        baseDataService.delProduct(id);
    }

    // 获取产品 带分页
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Page<Product> getProductPage(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "name", required = false) String name) throws Exception {
        return baseDataService.getAllProduct(page, size, name);
    }

    // 添加工序
    @RequestMapping(value = "/product/{id}/seq", method = RequestMethod.POST)
    public void addSeq(@RequestBody Seq seq, @PathVariable("id") int id) throws Exception {
        baseDataService.addSeqByProductId(id, seq);
    }

    // 查询工序
    @RequestMapping(value = "/product/{id}/seq", method = RequestMethod.GET)
    public List<Seq> getSeq(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
       return baseDataService.getSeqListByProductId(id, page, size);
    }

    // 删除工序
    @RequestMapping(value = "/product/{id}/seq/{idSeq}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('SYSTEM')")
    public void delSeq(@PathVariable("id") int id, @PathVariable("idSeq") int idSeq) throws Exception {
        baseDataService.delSeqById(id, idSeq);
    }

    // 给工序添加默认员工
    @RequestMapping(value = "/seq/{id}/staff", method = RequestMethod.POST)
    public void addStaff(@RequestBody Staff staff, @PathVariable("id") int id) throws Exception {
        baseDataService.addStaffBySeqId(id, staff);
    }

    // 获得工序的默认员工
    @RequestMapping(value = "/seq/{id}/staff", method = RequestMethod.GET)
    public List<Staff> getDefaultStaff(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return baseDataService.getStaffBySeqId(id, page, size);
    }

    // 删除工序默认员工
    @RequestMapping(value = "/seq/{id}/staff/{idStaff}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('SYSTEM')")
    public void delDefaultStaff(@PathVariable("id") int id, @PathVariable("idStaff") int idStaff) throws Exception {
        baseDataService.delStaffBySeqId(id, idStaff);
    }

    // 添加员工
    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    public void addStaff(@RequestBody Staff staff) throws Exception {
        staffService.addStaff(staff);
    }

    // 获取员工
    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public Page<Staff> getStaff(@RequestParam("page") int page,
                                 @RequestParam("size") int size,
                                 @RequestParam(name="status", required = false, defaultValue = "1") String status,
                                 @RequestParam(name="name", required = false) String name) throws Exception{
        return staffService.getStaffListByStatus(page, size, status, name);
    }

    // 添加仓库
    @RequestMapping(value = "/house", method = RequestMethod.POST)
    public void addHouse(@RequestBody House house) throws Exception {
        houseService.addHouse(house);
    }

    // 获取物料
    @RequestMapping(value = "/house/{id}", method = RequestMethod.GET)
    public Set<Origin> getOriginsByHouseId(@PathVariable("id") int id) throws Exception {
        return houseService.getOriginByHouseId(id);
    }

    // 添加物料
    @RequestMapping(value = "/house/{id}/origin", method = RequestMethod.POST)
    public void addOriginByHouseId(@PathVariable("id") int id, @RequestBody JSONObject object) throws Exception {
        String name = object.getString("name");
        int counts = Integer.valueOf(object.getString("counts"));
        houseService.addOriginByHouseId(id, name, counts);
    }

    // 删除物料
    @RequestMapping(value = "/house/{id}/origin/{idOrigin}", method = RequestMethod.DELETE)
    public void delOriginByHouseId(@PathVariable("id") int id, @PathVariable("idOrigin") int idOrigin, @RequestParam("counts") int counts) throws Exception {
        houseService.delOriginByHouseId(id, idOrigin, counts);
    }

    // 获取仓库 分页
    @RequestMapping(value = "/house", method = RequestMethod.GET)
    public Page<House> getHouses(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return houseService.getHouse(page, size);
    }

    // 获取员工工资
    @RequestMapping(value = "/salary", method = RequestMethod.GET)
    public Page<StaffSalary> getSalary(@RequestParam("page") int page
                                        ,@RequestParam("size") int size
                                        ,@RequestParam(name="moment[]", required = false) List<String> moment
                                        ,@RequestParam(name="name", required = false) String name) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date start = null;
        Date end = null;
        if (moment!=null) {
            start = formatter.parse(moment.get(0).substring(1, moment.get(0).length() - 1));
            end = formatter.parse(moment.get(1).substring(1, moment.get(1).length() -1));
        } else {
            // 默认情况 当前时间及前一个月算
            Calendar c = Calendar.getInstance();
            end = new Date();
            c.setTime(end);
            c.add(Calendar.MONTH, -1);
            start = c.getTime();
        }
        return staffService.getStaffSalaryByName(page, size, name, start, end);
    }
}
