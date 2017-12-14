package com.xy.service.impl;

import com.xy.dao.manage.Technics;
import com.xy.dao.manage.TechnicsRepository;
import com.xy.service.TechnicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TechnicsServiceImpl implements TechnicsService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TechnicsRepository technicsRepository;

    @Override
    public Technics addTechnics(Technics pr) {
        return  technicsRepository.save(pr);
    }

    @Override
    public  void delPTechnics(Technics pr) {
        technicsRepository.delete(pr);
    }

    @Override
    public Page<Technics> selectTechnics(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return  technicsRepository.findAll(pageRequest);
    }
}
