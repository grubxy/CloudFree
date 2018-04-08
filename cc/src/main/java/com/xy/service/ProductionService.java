package com.xy.service;

import com.xy.entity.Production;
import org.springframework.data.domain.Page;

public interface ProductionService {
    Production addProduction(Production p);

    void delProduction(Production p);

    Page<Production> selectProduction(int page, int size);
}
