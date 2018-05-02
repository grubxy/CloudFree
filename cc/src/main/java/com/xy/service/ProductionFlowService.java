package com.xy.service;

import com.xy.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ProductionFlowService {

    // 新增流程
    void addProductionFlow(ProductionFlow productionFlow) throws Exception;

    // 删除流程
    void delProductionFlow(String id) throws Exception;

    List<ProductionFlow> getAllProductionFlow(int page, int size) throws Exception;

    // 获取生产流程的所有工序
    List<Seq> getAllSeqByFlowId(String id) throws Exception;

    // 获取生产流程的工序详情
    Set<SeqInfo> getAllSeqInfoByFlowId(String id) throws Exception;

    void addConstructionByFlowId(String id, Construction construction) throws Exception;

    // 获取某流程所有施工单
    Set<Construction> getConstructionByFlowId(String id) throws Exception;

    // 根据状态获取工单
    List<Construction> getConstructionByStatus(int status, int page, int size) throws Exception;

    // 设置工单状态
    void setConstructionStatusById(String id, int idHouse, int status, int error, int cmpl) throws Exception;
}
