package com.xy.service.impl;

import com.xy.domain.*;
import com.xy.service.ProductionFlowService;
import com.xy.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProductionFlowImpl implements ProductionFlowService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductionFlowRepository productionFlowRepository;

    @Autowired
    private ConstructionRepository constructionRepository;

    // 新增流程
    @Override
    public void addProductionFlow(ProductionFlow productionFlow) throws Exception {

        Product product = new Product();

        // 生产编号
        productionFlow.setIdProduction(String.valueOf(SnowFlake.getInstance().nextId()));

        product = productRepository.findOne(productionFlow.getProduct().getIdProduct());

        Set<SeqInfo> seqInfoSet = new HashSet<SeqInfo>();
        for (Seq seq : product.getSeq()) {
            SeqInfo seqInfo = new SeqInfo();
            seqInfo.setSeq(seq);
            seqInfoSet.add(seqInfo);
        }

        productionFlow.setSeqInfo(seqInfoSet);

        productionFlowRepository.save(productionFlow);
    }

    // 删除流程
    @Override
    public void delProductionFlow(String id) throws Exception {
        productionFlowRepository.delete(id);
    }

    @Override
    public Page<ProductionFlow> getAllProductionFlow(int page, int size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        return productionFlowRepository.findAll(pageRequest);
    }

    // 获取生产流程的所有工序
    @Override
    public List<Seq> getAllSeqByFlowId(String id) throws Exception {
        ProductionFlow productionFlow = productionFlowRepository.findOne(id);
        List<Seq> seqList = new ArrayList<Seq>();
        for (SeqInfo tmp: productionFlow.getSeqInfo()) {
            seqList.add(tmp.getSeq());
        }
        return seqList;
    }

    // 增加施工单
    @Override
    public void addConstructionByFlowId(String id, Construction construction) throws Exception {
        construction.setSDate(new Date());
        construction.setEnumConstructStatus(EnumConstructStatus.WAITING);
        construction.setIdConstruct(String.valueOf(SnowFlake.getInstance().nextId()));

        ProductionFlow productionFlow = productionFlowRepository.findOne(id);

        //  校验数量依赖关系


        //  保存工单
        Set<Construction> constructionSet = new HashSet<Construction>();
        if (productionFlow.getConstructs().size()!=0) {
            productionFlow.getConstructs().add(construction);
        } else {
            constructionSet.add(construction);
            productionFlow.setConstructs(constructionSet);
        }
        productionFlowRepository.save(productionFlow);

    }

    // 设置工单状态
    @Override
    public void setConstructionStatusById(String id, int status) throws Exception {

        EnumConstructStatus enumConstructStatus = EnumConstructStatus.values()[status];

        Construction construction = constructionRepository.findOne(id);

        construction.setEnumConstructStatus(enumConstructStatus);

        constructionRepository.save(construction);

    }

}
