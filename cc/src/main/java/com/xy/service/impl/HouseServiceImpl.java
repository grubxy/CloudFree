package com.xy.service.impl;

import com.xy.domain.House;
import com.xy.domain.HouseRepository;
import com.xy.domain.Origin;
import com.xy.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService{

    @Autowired
    private HouseRepository houseRepository;

    // 新增仓库
    @Override
    public void addHouse(House house) throws Exception {
        houseRepository.save(house);
    }

    // 获取仓库列表
    @Override
    public List<House> getAllHouse() throws Exception {
        return houseRepository.findAll();
    }

    // 获取仓库 分页
    @Override
    public Page<House> getPageHouse(int page, int size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        return houseRepository.findAll(pageRequest);
    }

    // 获取某个仓库下物料
    @Override
    public Set<Origin> getOriginByHouseId(int id) throws Exception {
        return houseRepository.findOne(id).getOrigins();
    }
}
