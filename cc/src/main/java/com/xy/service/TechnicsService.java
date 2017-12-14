package com.xy.service;

import com.xy.dao.manage.Technics;
import org.springframework.data.domain.Page;

public interface TechnicsService {

    Technics addTechnics(Technics pr);

    void delPTechnics(Technics pr);

    Page<Technics> selectTechnics(int page, int size);
}
