package com.xy.controller;

import com.xy.dao.produce.Construct;
import com.xy.dao.produce.Production;
import com.xy.service.ConstructService;
import com.xy.service.ProductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/workflow")
public class WorkflowController {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductionService productionService;

    @Autowired
    private ConstructService constructService;

    @RequestMapping(value= "/production/add", method = RequestMethod.POST)
    @ResponseBody
    public Production addProduction(@RequestBody Production pr) {
        log.info("add production...");
        return productionService.addProduction(pr);
    }

    @RequestMapping(value="/production", method = RequestMethod.GET)
    @ResponseBody
    public Page<Production> selectProduction(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select production");
        return  productionService.selectProduction(page, size);
    }

    @RequestMapping(value = "/production/del", method=RequestMethod.POST)
    @ResponseBody
    public void delProduction(@RequestBody Production pr) {
        log.info("del production...");
        productionService.delProduction(pr);
    }

    @RequestMapping(value= "/construction/add", method = RequestMethod.POST)
    @ResponseBody
    public Construct addConstruction(@RequestBody Construct cs) {
        log.info("add construction...");
        return constructService.addConstruct(cs);
    }

    @RequestMapping(value="/construction", method = RequestMethod.GET)
    @ResponseBody
    public Page<Construct> selectConstruction(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select construction");
        return  constructService.selectConstruct(page, size);
    }

    @RequestMapping(value = "/construction/del", method=RequestMethod.POST)
    @ResponseBody
    public void delConstruction(@RequestBody Construct cs) {
        log.info("del construction...");
        constructService.delConstruct(cs);
    }

    @RequestMapping(value = "/construction/{pid}", method = RequestMethod.POST)
    @ResponseBody
    public void saveConstructionByPid(@PathVariable("pid") String pid , @RequestBody Construct cs) {
        log.info("save by pid...");
        constructService.saveConstructByPid(pid, cs);
    }

    @RequestMapping(value = "/constructionAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Construct> findAllConstruction() {
        return constructService.findAll();
    }

}
