package com.xy.service;

import com.xy.dao.manage.Technics;
import org.springframework.data.domain.Page;

public interface ProcedureService {
    Technics addProcedure(Technics pr);

    void delProcedure(Technics pr);

    Page<Technics> selectProcedure(int page, int size);
}
