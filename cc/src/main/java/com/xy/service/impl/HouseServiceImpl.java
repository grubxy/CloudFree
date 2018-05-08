package com.xy.service.impl;

import com.xy.domain.*;
import com.xy.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService{

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private MaterialRepository materialRepository;

    // 新增仓库
    @Override
    public void addHouse(House house) throws Exception {
        houseRepository.save(house);
    }

    // 获取仓库
    @Override
    public List<House> getHouse(int page, int size) throws Exception {
        Long total = (size !=0)?size:houseRepository.count();
        PageRequest pageRequest = new PageRequest(page,total.intValue());
        return houseRepository.findAll(pageRequest).getContent();
    }

    // 给仓库添加原料
    @Override
    public void addOriginByHouseId(int id, String name, int counts) throws Exception {

        // material不存在的话 添加材料
        Material material = materialRepository.findMaterialByName(name);
        if (material==null){
            Material tmpMaterial = new Material();
            tmpMaterial.setName(name);
            material = materialRepository.save(tmpMaterial);
        }

        House house = houseRepository.findOne(id);
        boolean bContain = false;
        if (house.getOrigins().size()!= 0) {
            for (Origin tmp : house.getOrigins()) {
                // 如果原料已经存在
                if (tmp.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                    tmp.setCounts(tmp.getCounts() + counts);
                    bContain = true;
                    break;
                }
            }
            if (!bContain) {
                // 不存在 则添加新 origin
                Origin origin = new Origin();
                origin.setCounts(counts);
                origin.setMaterial(material);
                house.getOrigins().add(origin);
            }
        } else {
            // 原来为空 直接 add
            Origin origin = new Origin();
            origin.setCounts(counts);
            origin.setMaterial(material);
            Set<Origin> originSet = new HashSet<Origin>();
            originSet.add(origin);
            house.setOrigins(originSet);
        }
        houseRepository.save(house);
    }

    // 获取某个仓库下物料
    @Override
    public Set<Origin> getOriginByHouseId(int id) throws Exception {
        return houseRepository.findOne(id).getOrigins();
    }
}
