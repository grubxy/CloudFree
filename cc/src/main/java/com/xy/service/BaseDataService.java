package com.xy.service;

import com.xy.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface BaseDataService {
    void addProduct(Product product) throws Exception;

    void delProduct(int id) throws Exception;

    void addSeqByProductId(int id, Seq seq) throws Exception;

    // 删除某个工序
    void delSeqById(int id) throws Exception;

    // 获取工序信息
    Set<Seq> getSeqListByProductId(int id) throws Exception;

    // 给某个工序添加默认员工
    void addStaffBySeqId(int id, Staff staff) throws Exception;

    Set<Staff> getStaffBySeqId(int id) throws Exception;

    // 获取所有基础数据信息
    List<Product> getAllProduct(int page, int size) throws Exception;
}
