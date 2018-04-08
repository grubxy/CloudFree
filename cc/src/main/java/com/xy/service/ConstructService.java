package com.xy.service;

import com.xy.entity.Material;
import com.xy.entity.Technics;
import com.xy.dao.pay.Employee;
import com.xy.entity.Construct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConstructService {
    Construct addConstruct(Construct cs);

    void delConstruct(Construct cs);

    Page<Construct> selectConstruct(int page, int size);

    void saveConstructByPid(String pid, Construct cs);

    List<Construct> findAll();

    void saveMaterialByCid(String cid, Material mt);

    void saveTechnicsByCid(String cid, Technics technics);

    void saveEmployeeByCid(String cid, Employee employee);
}
