package com.xy.service.impl;

import com.xy.dao.produce.Construct;
import com.xy.dao.produce.Production;
import com.xy.dao.produce.ProductionRepository;
import com.xy.service.ProductionService;
import com.xy.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductionImpl implements ProductionService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductionRepository productionRepository;

    @Override
    public Production addProduction(Production p) {
        SnowFlake sf = new SnowFlake(0, 0);
        if (p.getPid() == null) {
            p.setPid(String.valueOf(sf.nextId()));
        }
        return  productionRepository.save(p);
    }

    @Override
    public void delProduction(Production p) {
        productionRepository.delete(p);
    }

    @Override
    public Page<Production> selectProduction(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return  productionRepository.findAll(pageRequest);
    }
}
