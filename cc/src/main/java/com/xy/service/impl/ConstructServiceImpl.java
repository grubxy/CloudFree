package com.xy.service.impl;

import com.google.common.collect.Lists;
import com.xy.entity.Material;
import com.xy.entity.MaterialRepository;
import com.xy.entity.Technics;
import com.xy.entity.TechnicsRepository;
import com.xy.dao.pay.Employee;
import com.xy.dao.pay.EmployeeRepository;
import com.xy.entity.Construct;
import com.xy.entity.ConstructRepository;
import com.xy.entity.Production;
import com.xy.entity.ProductionRepository;
import com.xy.service.ConstructService;
import com.xy.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConstructServiceImpl implements ConstructService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConstructRepository constructRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private TechnicsRepository technicsRepository;

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
    public void saveConstructByPid(String pid, Construct cs) {
        Production pd = productionRepository.findOne(pid);
        SnowFlake sf = SnowFlake.getInstance();
        if (cs.getCid() == null) {
            cs.setCid(String.valueOf(sf.nextId()));
        }

        // 获取员工
        cs.setEmployee(employeeRepository.findOne(cs.getEmployee().getEid()));
        // 获取材料
        cs.setMaterial(materialRepository.findOne(cs.getMaterial().getMcode()));
        // 获取工序
        cs.setTechnics(technicsRepository.findOne(cs.getTechnics().getTcode()));

        // 次品数量
        if (pd.getErr_counts() != null) {
            pd.setErr_counts(pd.getErr_counts() + cs.getErr_counts());
        } else {
            pd.setErr_counts(cs.getErr_counts());
        }
        if (pd.getCmpl_counts() != null) {
            // 完成数量
            pd.setCmpl_counts(pd.getCmpl_counts() + cs.getCmpl_counts());
        } else {
            pd.setCmpl_counts(cs.getCmpl_counts());
        }
        if (pd.getFact_counts()!= null) {
            // 总数量
            pd.setFact_counts(pd.getFact_counts() + cs.getCmpl_counts() + cs.getErr_counts());
        } else
        {
            pd.setFact_counts(cs.getCmpl_counts() + cs.getErr_counts());
        }

        Set<Construct> csSet = new HashSet<Construct>();
        if (pd.getConstructs() != null) {
            csSet.addAll(pd.getConstructs());
        }
        csSet.add(cs);
        // save
        pd.setConstructs(csSet);
        productionRepository.save(pd);
    }

    @Override
    public List<Construct> findAll(){
        return Lists.newArrayList(constructRepository.findAll());
    }

    @Override
    public void saveMaterialByCid(String cid, Material mt) {
        Construct construct = constructRepository.findOne(cid);
        construct.setMaterial(mt);
        constructRepository.save(construct);
    }

    @Override
    public void saveTechnicsByCid(String cid, Technics technics) {
        Construct construct = constructRepository.findOne(cid);
        construct.setTechnics(technics);
        constructRepository.save(construct);
    }

    @Override
    public  void saveEmployeeByCid(String cid, Employee employee) {
        Construct construct = constructRepository.findOne(cid);
        construct.setEmployee(employee);
        constructRepository.save(construct);
    }
}
