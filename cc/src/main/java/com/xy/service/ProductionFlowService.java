package com.xy.service;

import com.xy.domain.Construction;
import com.xy.domain.ProductionFlow;
import com.xy.domain.Seq;
import com.xy.domain.Staff;

import java.util.List;

public interface ProductionFlowService {

    // 新增流程
    void addProductionFlow(ProductionFlow productionFlow) throws Exception;

    // 删除流程
    void delProductionFlow(String id) throws Exception;

    // 获取生产流程的所有工序
    List<Seq> getAllSeqByFlowId(String id) throws Exception;

    void addConstructionByFlowId(String id, Construction construction) throws Exception;

    void addStaffByConstrId(String id, Staff staff) throws Exception;
}
