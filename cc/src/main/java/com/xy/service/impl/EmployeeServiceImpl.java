package com.xy.service.impl;

import com.google.common.collect.Lists;
import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee em){
        return employeeRepository.save(em);
    }

    @Override
    public void delEmployee(Employee em) {
        employeeRepository.delete(em);
    }

    @Override
    public Page<Employee> selectEmployee(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return  employeeRepository.findAll(pageRequest);
    }

    @Override
    public List<Employee> findAll(){
        return Lists.newArrayList(employeeRepository.findAll());
    }
}
