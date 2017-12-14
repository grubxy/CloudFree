package com.xy.service;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import com.xy.dao.pay.Employee;
import com.xy.dao.produce.Construct;
import org.springframework.data.domain.Page;

public interface ConstructService {
    Construct addConstruct(Construct cs);

    void delConstruct(Construct cs);

    Page<Construct> selectConstruct(int page, int size);

    void saveConstructByPid(Long pid, Construct cs);

    void saveMaterialByCid(Long cid, Material mt);

    void saveTechnicsByCid(Long cid, Technics technics);

    void saveEmployeeByCid(Long cid, Employee employee);
}
