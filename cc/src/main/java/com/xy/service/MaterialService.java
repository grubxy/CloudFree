package com.xy.service;

import com.xy.dao.manage.Material;
import org.springframework.data.domain.Page;

public interface MaterialService {
    Material addMaterial(Material mt);

    void delMaterial(Material mt);

    Page<Material> selectMaterial(int page, int size);
}
