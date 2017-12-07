package com.xy.controller;

import com.xy.dao.pay.Employee;
import com.xy.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/pay")
public class PayController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayService payService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee em) {
        log.info("add user...");
        return payService.addEmployee(em);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ResponseBody
    public Page<Employee> selectEmployee(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("select employee...");
        return payService.selectEmployee(page, size);
    }
}
