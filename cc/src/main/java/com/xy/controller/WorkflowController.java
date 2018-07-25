package com.xy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xy.domain.*;
import com.xy.service.ProductionFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Slf4j
public class WorkflowController {

    @Autowired
    private ProductionFlowService productionFlowService;

    // 添加生产流程
    @RequestMapping(value = "/flow", method = RequestMethod.POST)
    public void addFlow(@RequestBody ProductionFlow productionFlow) throws Exception {
        productionFlowService.addProductionFlow(productionFlow);
    }

    // 获取生产流程信息
    @RequestMapping(value = "/flow", method = RequestMethod.GET)
    public Page<ProductionFlow> getFlows(@RequestParam("page") int page,
                                         @RequestParam("size") int size ,
                                         @RequestParam(name = "id", required = false) String id,
                                         @RequestParam(name = "name", required = false) String name,
                                         @RequestParam(value = "moment[]", required = false) List<String> moment) throws Exception{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date start = null;
        Date end = null;
        if (moment!=null)
        {
            start = formatter.parse(moment.get(0).substring(1, moment.get(0).length() - 1));
            end = formatter.parse(moment.get(1).substring(1, moment.get(1).length() -1));
        }
        return productionFlowService.getAllProductionFlow(page, size, id, name, start, end);
    }

    // 获取生产产品工序
    @RequestMapping(value = "/flow/{id}/seq", method = RequestMethod.GET)
    public List<Seq> getSeq(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getAllSeqByFlowId(id);
    }

    // 获取生产流程的工序详情
    @RequestMapping(value = "/flow/{id}/seqinfo", method = RequestMethod.GET)
    public List<SeqInfo> getSeqInfo(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getAllSeqInfoByFlowId(id);
    }

    // 添加工单
    @RequestMapping(value = "/flow/{id}/construction", method = RequestMethod.POST)
    public void addConstructionByFlow(@RequestBody Construction construction, @PathVariable("id") String id) throws Exception {
        productionFlowService.addConstructionByFlowId(id, construction);
    }

    // 获取工单
    @RequestMapping(value = "/flow/{id}/construction", method = RequestMethod.GET)
    public List<Construction> getConstruction(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getConstructionByFlowId(id);
    }

    // 获取工单
    @RequestMapping(value = "/construction", method = RequestMethod.GET)
    public Page<Construction> getConstructionsByStatus(@RequestParam(name="status", defaultValue = "0", required = false) String status,
                                                       @RequestParam(name="id", required = false)String id,
                                                       @RequestParam(name="name", required = false) String name,
                                                       @RequestParam(name="staff", required = false) String staff,
                                                       @RequestParam("page") int page,
                                                       @RequestParam(value = "size") int size,
                                                       @RequestParam(value = "moment[]", required = false) List<String> moment) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date start = null;
        Date end = null;
        if (moment!=null)
        {
            start = formatter.parse(moment.get(0).substring(1, moment.get(0).length() - 1));
            end = formatter.parse(moment.get(1).substring(1, moment.get(1).length() -1));
        }

        return productionFlowService.getConstructionByStatus(page, size, status, id, name ,staff, start, end);
    }

    // 设置工单状态
    @RequestMapping(value = "/construction/{id}", method = RequestMethod.PATCH)
    public void setStatus(@RequestBody JSONObject obj, @PathVariable("id") String id) throws Exception {
        int status = obj.getIntValue("status");
        int idHouse = obj.getIntValue("idHouse");
        int error = obj.getIntValue("error");
        int cmpl = obj.getIntValue("cmpl");
        productionFlowService.setConstructionStatusById(id, idHouse, status, error, cmpl);
    }
}
