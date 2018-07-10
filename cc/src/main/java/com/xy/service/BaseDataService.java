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
    List<Seq> getSeqListByProductId(int id, int page, int size) throws Exception;

    // 给某个工序添加默认员工
    void addStaffBySeqId(int id, Staff staff) throws Exception;

    List<Staff> getStaffBySeqId(int id, int page, int size) throws Exception;

    // 获取所有基础数据信息
    Page<Product> getAllProduct(int page, int size, String name) throws Exception;
}
