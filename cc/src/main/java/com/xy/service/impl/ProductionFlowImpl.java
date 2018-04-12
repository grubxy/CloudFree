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

    @Autowired
    private SeqInfoRepository seqInfoRepository;

    @Autowired
    private OriginRepository originRepository;

    // 新增流程
    @Override
    public void addProductionFlow(ProductionFlow productionFlow) throws Exception {

        // 生产编号
        productionFlow.setIdProduction(String.valueOf(SnowFlake.getInstance().nextId()));

        Product product = productRepository.findOne(productionFlow.getProduct().getIdProduct());

        Set<SeqInfo> seqInfoSet = new HashSet<SeqInfo>();
        for (Seq seq : product.getSeq()) {
            SeqInfo seqInfo = new SeqInfo();

            seqInfo.setSeq(seq);

            seqInfo.setCmplCounts(0);
            seqInfo.setErrCounts(0);
            seqInfo.setDoingCounts(0);

            // 如果是第一个工序 设置
            if (seq.getSeqIndex() == 1) {
                seqInfo.setDstCounts(productionFlow.getDstCounts());
            } else {
                seqInfo.setDstCounts(0);
            }
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

    // 获取生产流程 分页
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

    // 获取生产流程的工序详情
    @Override
    public Set<SeqInfo> getAllSeqInfoByFlowId(String id) throws Exception {
        ProductionFlow productionFlow = productionFlowRepository.findOne(id);
        return productionFlow.getSeqInfo();
    }

    // 增加施工单
    @Override
    public void addConstructionByFlowId(String id, Construction construction) throws Exception {

        construction.setSDate(new Date());
        construction.setEnumConstructStatus(EnumConstructStatus.WAITING);
        construction.setIdConstruct(String.valueOf(SnowFlake.getInstance().nextId()));

        ProductionFlow productionFlow = productionFlowRepository.findOne(id);

        /**  校验数量依赖关系 **/

        // 查找该工单对应的工序详情
        for (SeqInfo seqInfo:productionFlow.getSeqInfo()) {
            if (seqInfo.getSeq().getIdSeq() == construction.getSeq().getIdSeq()) {
                // 如果是第一道工序
                if (seqInfo.getSeq().getSeqIndex() == 1) {
                    // 设置数量关系
                    if (construction.getDstCount() <= seqInfo.getDstCounts()
                            && construction.getDstCount() > 0) {
                        seqInfo.setDoingCounts(construction.getDstCount());
                        seqInfo.setDstCounts(seqInfo.getDstCounts() - construction.getDstCount());
                    } else {
                        throw new UserException(ErrorCode.CONSTRUCTION_COUNTS_ERROR.getCode(), ErrorCode.CONSTRUCTION_COUNTS_ERROR.getMsg());
                    }

                } else {
                    // 非第一道工序 依赖之前工序完成量
                    for (SeqInfo before:productionFlow.getSeqInfo()) {
                        // 找到前1道工序
                        if (before.getSeq().getSeqIndex() == seqInfo.getSeq().getSeqIndex() - 1) {
                            if (construction.getDstCount() <= before.getCmplCounts()
                                    && construction.getDstCount() > 0) {
                                before.setCmplCounts(before.getCmplCounts() - construction.getDstCount());
                                seqInfo.setDoingCounts(construction.getDstCount());
                            }else {
                                throw new UserException(ErrorCode.CONSTRUCTION_COUNTS_ERROR.getCode(), ErrorCode.CONSTRUCTION_COUNTS_ERROR.getMsg());
                            }
                        }
                        seqInfoRepository.save(before);
                    }
                }
                seqInfoRepository.save(seqInfo);
            }
        }

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

    // 获取某流程所有施工单
    @Override
    public Set<Construction> getConstructionByFlowId(String id) throws Exception {
        ProductionFlow productionFlow = productionFlowRepository.findOne(id);
        return productionFlow.getConstructs();
    }

    // 设置工单状态
    @Override
    public void setConstructionStatusById(String id, int status) throws Exception {

        EnumConstructStatus enumConstructStatus = EnumConstructStatus.values()[status];

        Construction construction = constructionRepository.findOne(id);
        construction.setEnumConstructStatus(enumConstructStatus);
        constructionRepository.save(construction);
    }

    // 工单完成，将工单制作完成的物料入库
    @Override
    public void ConstructToOriginBySeqInfoId(String id, House house) throws Exception {
    }

}
