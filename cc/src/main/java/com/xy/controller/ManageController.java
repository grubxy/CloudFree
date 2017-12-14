package com.xy.controller;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import com.xy.service.MaterialService;
import com.xy.service.TechnicsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/manage")
public class ManageController {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TechnicsService technicsService;

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


    @RequestMapping(value = "/technics/add", method = RequestMethod.POST)
    @ResponseBody
    public Technics addTechnics(@RequestBody Technics pr) {
        log.info("procedure add...");
        return  technicsService.addTechnics(pr);
    }

    @RequestMapping(value = "/technics/del", method = RequestMethod.POST)
    @ResponseBody
    public void delTechnics(@RequestBody Technics pr) {
        log.info("procedure del...");
        technicsService.delPTechnics(pr);
    }

    @RequestMapping(value = "/technics", method = RequestMethod.GET)
    @ResponseBody
    public Page<Technics> selectTechnics(@RequestParam("page") int page, @RequestParam("size") int size){
        log.info("select procedure");
        return technicsService.selectTechnics(page, size);
    }

    @RequestMapping(value = "/technicsAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Technics> allTechnics(){
        return technicsService.findAll();
    }
}
