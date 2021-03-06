package com.xy.service;

import com.xy.domain.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProductionFlowService {

    // 新增流程
    void addProductionFlow(ProductionFlow productionFlow) throws Exception;

    // 删除流程
    void delProductionFlow(String id) throws Exception;

    // 获取生产流程 分页
    Page<ProductionFlow> getAllProductionFlow(int page, int size, String id, String name) throws Exception;

    // 获取生产流程的所有工序
    List<Seq> getAllSeqByFlowId(String id) throws Exception;

    // 获取生产流程的工序详情
    List<SeqInfo> getAllSeqInfoByFlowId(String id) throws Exception;

    void addConstructionByFlowId(String id, Construction construction) throws Exception;

    // 获取某流程所有施工单
    List<Construction> getConstructionByFlowId(String id) throws Exception;

    // 根据状态获取工单
    Page<Construction> getConstructionByStatus(int page, int size, String status, String id, String name, String staff, Date start, Date end) throws Exception;

    // 设置工单状态
    void setConstructionStatusById(String id, int idHouse, int status, int error, int cmpl) throws Exception;
}
