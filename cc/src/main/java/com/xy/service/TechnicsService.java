package com.xy.service;

import com.xy.entity.Technics;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TechnicsService {

    Technics addTechnics(Technics pr);

    void delPTechnics(Technics pr);

    Page<Technics> selectTechnics(int page, int size);

    List<Technics> findAll();
}
