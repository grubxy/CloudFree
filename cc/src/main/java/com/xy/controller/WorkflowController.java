package com.xy.controller;

import com.alibaba.fastjson.JSONObject;
import com.xy.domain.Construction;
import com.xy.domain.ProductionFlow;
import com.xy.domain.Seq;
import com.xy.domain.SeqInfo;
import com.xy.service.ProductionFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


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
    public List<ProductionFlow> getFlows(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception{
        return productionFlowService.getAllProductionFlow(page, size);
    }

    // 获取生产产品工序
    @RequestMapping(value = "/flow/{id}/seq", method = RequestMethod.GET)
    public List<Seq> getSeq(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getAllSeqByFlowId(id);
    }

    // 获取生产流程的工序详情
    @RequestMapping(value = "/seqinfo/{id}", method = RequestMethod.GET)
    public Set<SeqInfo> getSeqInfo(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getAllSeqInfoByFlowId(id);
    }

    // 添加工单
    @RequestMapping(value = "/flow/{id}/construction", method = RequestMethod.POST)
    public void addConstructionByFlow(@RequestBody Construction construction, @PathVariable("id") String id) throws Exception {
        productionFlowService.addConstructionByFlowId(id, construction);
    }

    // 获取工单
    @RequestMapping(value = "/flow/{id}/construction", method = RequestMethod.GET)
    public Set<Construction> getConstruction(@PathVariable("id") String id) throws Exception {
        return productionFlowService.getConstructionByFlowId(id);
    }

    // 获取工单
    @RequestMapping(value = "/construction", method = RequestMethod.GET)
    public Page<Construction> getConstructionsByStatus(@RequestParam("status") int status, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return productionFlowService.getConstructionByStatus(status, page, size);
    }

    // 设置工单状态
    @RequestMapping(value = "/construction/{id}", method = RequestMethod.POST)
    public void setStatus(@RequestBody JSONObject obj, @PathVariable("id") String id) throws Exception {
        int status = obj.getIntValue("status");
        int idHouse = obj.getIntValue("idHouse");
        int error = obj.getIntValue("error");
        int cmpl = obj.getIntValue("cmpl");
        productionFlowService.setConstructionStatusById(id, idHouse, status, error, cmpl);
    }

}
