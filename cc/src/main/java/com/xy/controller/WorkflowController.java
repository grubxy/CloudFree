package com.xy.controller;

import com.xy.domain.Construction;
import com.xy.domain.ProductionFlow;
import com.xy.domain.Seq;
import com.xy.service.ProductionFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/workflow")
@Slf4j
public class WorkflowController {

    @Autowired
    private ProductionFlowService productionFlowService;

    // 添加生产流程
    @RequestMapping(value = "/addflow", method = RequestMethod.POST)
    public void addFlow(@RequestBody ProductionFlow productionFlow) throws Exception {
        productionFlowService.addProductionFlow(productionFlow);
    }

    // 获取生产流程信息
    @RequestMapping(value = "/getflow", method = RequestMethod.GET)
    public Page<ProductionFlow> getFlows(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception{
        return productionFlowService.getAllProductionFlow(page, size);
    }

    @RequestMapping(value = "/addConstruction/{idFlow}", method = RequestMethod.POST)
    public void addConstructionByFlow(@RequestBody Construction construction, @PathVariable("idFlow") String id) throws Exception {
        productionFlowService.addConstructionByFlowId(id, construction);
    }
}
