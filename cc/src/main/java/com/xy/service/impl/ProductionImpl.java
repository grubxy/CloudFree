package com.xy.service.impl;

import com.xy.dao.produce.Construct;
import com.xy.dao.produce.Production;
import com.xy.dao.produce.ProductionRepository;
import com.xy.service.ProductionService;
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

    @Override
    public void saveConstructByPid(Long pid, Construct cs) {
        Production pd = productionRepository.findOne(pid);
        Set<Construct> csSet = new HashSet<Construct>();
        if (pd.getConstructs() == null) {
            csSet.add(cs);
        } else {
            csSet.addAll(pd.getConstructs());
            csSet.add(cs);
        }
        // 次品数量
        pd.setErr_counts(pd.getErr_counts() + cs.getErr_counts());
        // 完成数量
        pd.setCmpl_counts(pd.getCmpl_counts() + cs.getCmpl_counts());
        // 总数量
        pd.setFact_counts(pd.getFact_counts() + cs.getCmpl_counts() + cs.getErr_counts());
        csSet.add(cs);
        pd.setConstructs(csSet);

        // save
        productionRepository.save(pd);
    }
}
