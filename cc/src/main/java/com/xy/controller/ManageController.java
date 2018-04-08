package com.xy.controller;

import com.xy.entity.Material;
import com.xy.entity.Technics;
import com.xy.dao.pay.Employee;
import com.xy.service.EmployeeService;
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

    @Autowired
    private EmployeeService employeeService;

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

    @RequestMapping(value = "/materialAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Material> allMaterial() {
        log.info("all material...");
        return materialService.findAll();
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
        log.info("get all technics...");
        return technicsService.findAll();
    }


    @RequestMapping(value="/employee/add", method = RequestMethod.POST)
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee em) {
        log.info("add employee...");
        return employeeService.addEmployee(em);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ResponseBody
    public Page<Employee> selectEmployee(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select employee...");
        return employeeService.selectEmployee(page, size);
    }

    @RequestMapping(value = "/employee/del", method = RequestMethod.POST)
    @ResponseBody
    public void delEmployee(@RequestBody Employee em) {
        log.info("delete employee");
        employeeService.delEmployee(em);
    }

    @RequestMapping(value = "/employeeAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> allEmployee(){
        log.info("get all employee...");
        return employeeService.findAll();
    }
}
