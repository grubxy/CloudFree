package com.xy.service.impl;

import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.model.EmployeeSelect;
import com.xy.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService{

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
}
