package com.xy.service;

import com.xy.dao.produce.Construct;
import org.springframework.data.domain.Page;

public interface ConstructService {
    Construct addConstruct(Construct cs);

    void delConstruct(Construct cs);

    Page<Construct> selectConstruct(int page, int size);
}
