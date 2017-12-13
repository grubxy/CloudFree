package com.xy.service.impl;

import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.dao.produce.Construct;
import com.xy.dao.produce.ConstructRepository;
import com.xy.service.ConstructService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConstructServiceImpl implements ConstructService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConstructRepository constructRepository;

    @Override
    public Construct addConstruct(Construct cs) {
        return  constructRepository.save(cs);
    }

    @Override
    public void delConstruct(Construct cs) {
        constructRepository.delete(cs);
    }

    @Override
    public Page<Construct> selectConstruct(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return constructRepository.findAll(pageRequest);
    }

}
