package com.xy.controller;

import com.xy.dao.manage.Material;
import com.xy.service.MaterialService;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Material add(@RequestBody Material mt){
        log.info("add material...");
        return materialService.addMaterial(mt);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public void del(@RequestBody Material mt) {
        log.info("delete material...");
        materialService.delMaterial(mt);
    }

    @RequestMapping(value = "/material", method = RequestMethod.GET)
    @ResponseBody
    public Page<Material> select(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select material...");
        return materialService.selectMaterial(page, size);
    }
}
