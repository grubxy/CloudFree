package com.xy.service.impl;

import com.xy.domain.*;
import com.xy.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
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
    public Page<House> getHouse(int page, int size) throws Exception {
        Long total = (size !=0)?size:houseRepository.count();
        if (total == 0) {
            throw new UserException(ErrorCode.HOUSE_NO_ERROR.getCode(), ErrorCode.HOUSE_NO_ERROR.getMsg());
        }
        Pageable pageable = new PageRequest(page,total.intValue(), new Sort(Sort.Direction.DESC, "idHouse"));
        return houseRepository.findAll(pageable);
    }

    // 给仓库添加原料
    @Override
    public void addOriginByHouseId(int id, String name, int counts) throws Exception {

        // material不存在的话 添加材料
        name = name.trim();
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

    // 删除原料
    @Override
    public void delOriginByHouseId(int id, int idOrigin, int counts) throws Exception {
        House house = houseRepository.findOne(id);
        for (Origin origin:house.getOrigins()) {
            if (origin.getIdOrigin()==idOrigin) {
                if ( counts > origin.getCounts() || counts < 0) {
                    throw new UserException(ErrorCode.HOUSE_ORIGIN_COUNT_ERROR.getCode(), ErrorCode.HOUSE_ORIGIN_COUNT_ERROR.getMsg());
                } else if (origin.getCounts() == counts) {
                    // 相等 删除house中origin
                    house.getOrigins().remove(origin);
                    houseRepository.save(house);
                    originRepository.delete(origin.getIdOrigin());
                } else {
                    // 处理数量关系
                    origin.setCounts(origin.getCounts() - counts);
                    originRepository.save(origin);
                }
            } else {
                throw new UserException(ErrorCode.HOUSE_ORIGIN_ID_ERROR.getCode(), ErrorCode.HOUSE_ORIGIN_ID_ERROR.getMsg());
            }
        }
    }


    // 获取某个仓库下物料
    @Override
    public Set<Origin> getOriginByHouseId(int id) throws Exception {
        return houseRepository.findOne(id).getOrigins();
    }
}
