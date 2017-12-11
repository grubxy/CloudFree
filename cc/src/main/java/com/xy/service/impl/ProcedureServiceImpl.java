package com.xy.service.impl;

import com.xy.dao.manage.Technics;
import com.xy.dao.manage.TechnicsRepository;
import com.xy.service.ProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TechnicsRepository procedureRepository;

    @Override
    public Technics addProcedure(Technics pr) {
        return  procedureRepository.save(pr);
    }

    @Override
    public  void delProcedure(Technics pr) {
        procedureRepository.delete(pr);
    }

    @Override
    public Page<Technics> selectProcedure(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return  procedureRepository.findAll(pageRequest);
    }
}
