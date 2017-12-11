package com.xy.controller;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import com.xy.service.MaterialService;
import com.xy.service.ProcedureService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/manage")
public class ManageController {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProcedureService procedureService;

    @RequestMapping(value = "/material/add", method = RequestMethod.POST)
    @ResponseBody
    public Material addMaterial(@RequestBody Material mt){
        log.info("add material...");
        return materialService.addMaterial(mt);
    }

    @RequestMapping(value = "/material/del", method = RequestMethod.POST)
    @ResponseBody
    public void delMaterial(@RequestBody Material mt) {
        log.info("delete material...");
        materialService.delMaterial(mt);
    }

    @RequestMapping(value = "/material", method = RequestMethod.GET)
    @ResponseBody
    public Page<Material> selectMaterial(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select material...");
        return materialService.selectMaterial(page, size);
    }


    @RequestMapping(value = "/procedure/add", method = RequestMethod.POST)
    @ResponseBody
    public Technics addProcedure(@RequestBody Technics pr) {
        log.info("procedure add...");
        return  procedureService.addProcedure(pr);
    }

    @RequestMapping(value = "/procedure/del", method = RequestMethod.POST)
    @ResponseBody
    public void delProcedure(@RequestBody Technics pr) {
        log.info("procedure del...");
        procedureService.delProcedure(pr);
    }

    @RequestMapping(value = "/procedure", method = RequestMethod.GET)
    @ResponseBody
    public Page<Technics> selectProcedure(@RequestParam("page") int page, @RequestParam("size") int size){
        log.info("select procedure");
        return procedureService.selectProcedure(page, size);
    }
}
