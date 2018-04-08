package com.xy.service;

import com.xy.entity.Product;
import com.xy.entity.Seq;
import com.xy.entity.Staff;
import org.springframework.data.domain.Page;

public interface BaseDataService {
    void addProduct(Product product) throws Exception;

    void delProduct(int id) throws Exception;

    void addSeqByProductId(int id, Seq seq) throws Exception;

    // 删除某个工序
    void delSeqById(int id) throws Exception;

    // 给某个工序添加默认员工
    void addStaffBySeqId(int id, Staff staff) throws Exception;

    // 获取所有基础数据信息
    Page<Product> getAllProduct(int page, int size) throws Exception;
}
