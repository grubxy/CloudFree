package com.xy.service.impl;

import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class PayServiceImpl implements PayService{

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee em){
        return employeeRepository.save(em);
    }
}
