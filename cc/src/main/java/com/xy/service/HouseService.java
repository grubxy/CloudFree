package com.xy.service;

import com.xy.domain.House;
import com.xy.domain.Origin;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface HouseService {
    // 新增仓库
    void addHouse(House house) throws Exception;

    // 获取仓库列表
    List<House> getAllHouse() throws Exception;

    // 获取仓库 分页
    Page<House> getPageHouse(int page, int size) throws Exception;

    // 获取某个仓库下物料
    Set<Origin> getOriginByHouseId(int id) throws Exception;
}
