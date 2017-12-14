package com.xy.service.impl;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.dao.produce.Construct;
import com.xy.dao.produce.ConstructRepository;
import com.xy.dao.produce.Production;
import com.xy.dao.produce.ProductionRepository;
import com.xy.service.ConstructService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ConstructServiceImpl implements ConstructService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConstructRepository constructRepository;

    @Autowired
    private ProductionRepository productionRepository;

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

    @Override
    public void saveMaterialByCid(Long cid, Material mt) {
        Construct construct = constructRepository.findOne(cid);
        construct.setMaterial(mt);
        constructRepository.save(construct);
    }

    @Override
    public void saveTechnicsByCid(Long cid, Technics technics) {
        Construct construct = constructRepository.findOne(cid);
        construct.setTechnics(technics);
        constructRepository.save(construct);
    }

    @Override
    public  void saveEmployeeByCid(Long cid, Employee employee) {
        Construct construct = constructRepository.findOne(cid);
        construct.setEmployee(employee);
        constructRepository.save(construct);
    }
}
