package com.xy.service.impl;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.MaterialRepository;
import com.xy.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material addMaterial(Material mt) {
        return  materialRepository.save(mt);
    }

    @Override
    public void delMaterial(Material mt) {
        materialRepository.delete(mt);
    }

    @Override
    public Page<Material> selectMaterial(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return materialRepository.findAll(pageRequest);
    }
}