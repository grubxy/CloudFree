package com.xy.service;

import com.xy.domain.House;
import com.xy.domain.Origin;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface HouseService {
    // 新增仓库
    void addHouse(House house) throws Exception;

    // 获取仓库
    List<House> getHouse(int page, int size) throws Exception;

    // 给仓库添加原料
    void addOriginByHouseId(int id, String name, int counts) throws Exception;

    // 删除原料
    void delOriginByHouseId(int id, int idOrigin, int counts) throws Exception;

    // 获取某个仓库下物料
    Set<Origin> getOriginByHouseId(int id) throws Exception;
}
