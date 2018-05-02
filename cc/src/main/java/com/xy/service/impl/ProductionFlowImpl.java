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
    private HouseRepository houseRepository;

    // 新增流程
    @Override
    public void addProductionFlow(ProductionFlow productionFlow) throws Exception {

        // 生产编号
        productionFlow.setIdProduction(String.valueOf(SnowFlake.getInstance().nextId()));
        productionFlow.setCmplCounts(0);
        productionFlow.setErrCounts(0);

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
    public List<ProductionFlow> getAllProductionFlow(int page, int size) throws Exception {
        Long total = (size !=0)?size:productionFlowRepository.count();
        PageRequest pageRequest = new PageRequest(page, total.intValue());
        return productionFlowRepository.findAll(pageRequest).getContent();
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

        construction.setCmplCount(0);
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

    // 根据状态获取工单
    @Override
    public List<Construction> getConstructionByStatus(int status, int page, int size) throws Exception {
        Long total = (size !=0)?size:constructionRepository.count();
        Pageable pageable = new PageRequest(page, total.intValue());
        return constructionRepository.findConstructionByEnumConstructStatus(EnumConstructStatus.values()[status], pageable).getContent();
    }

    // 根据工单获取对应工序详情
    private SeqInfo getSeqInfoFromConstruction(Construction construction) throws Exception {
        for (SeqInfo seqInfo:construction.getProduction().getSeqInfo()) {
            if (seqInfo.getSeq().getIdSeq() == construction.getSeq().getIdSeq()) {
                return seqInfo;
            }
        }
        log.error("工单中的工序，找不到相对应的流程对应工序详情");
        throw new UserException(ErrorCode.CONSTRUCTION_SEQINFO_ERROR.getCode(), ErrorCode.CONSTRUCTION_SEQINFO_ERROR.getMsg());
    }

    // 设置工单状态
    @Override
    public void setConstructionStatusById(String id, int idHouse, int status, int error, int cmpl) throws Exception {

        EnumConstructStatus enumConstructStatus = EnumConstructStatus.values()[status];

        Construction construction = constructionRepository.findOne(id);
        SeqInfo seqInfo = getSeqInfoFromConstruction(construction);

        // 根据不同的状态做处理
        switch (enumConstructStatus) {
            case WORKING:
                // 物料出库 设置工序详情 Doing 个数
                seqInfo.setDoingCounts(seqInfo.getDoingCounts() + construction.getDstCount());
                seqInfoRepository.save(seqInfo);
                break;
            case COMPLETE:
                if (cmpl + error != construction.getDstCount()) {
                    throw new UserException(ErrorCode.CONSTRUCTION_COMPLETECOUNTS_ERROR.getCode(), ErrorCode.CONSTRUCTION_COMPLETECOUNTS_ERROR.getMsg());
                }

                // 设置工单 完成 废料个数
                construction.setCmplCount(cmpl);
                construction.setErrCount(error);

                break;
            case STORED:
                // 将完成物料 入库
                Origin origin = new Origin();
                origin.setCounts(construction.getCmplCount());
                origin.setMaterial(seqInfo.getSeq().getDstMaterial());

                House house = houseRepository.findOne(idHouse);

                // 仓库原料不为空
                if (house.getOrigins().size()!=0) {
                    for (Origin tmp: house.getOrigins()) {
                        // 如果原料已经存在
                        if (tmp.getMaterial().getIdMaterial() == origin.getMaterial().getIdMaterial()) {
                            tmp.setCounts(tmp.getCounts() +  origin.getCounts());
                        }
                    }
                } else {
                    Set<Origin> originSet = new HashSet<Origin>();
                    originSet.add(origin);
                    house.setOrigins(originSet);
                }
                houseRepository.save(house);

                // 设置工序详情 完成 废料个数，暂定与工单及Doing个数相等
                seqInfo.setDoingCounts(seqInfo.getDoingCounts() - construction.getCmplCount() - construction.getErrCount());
                seqInfo.setCmplCounts(seqInfo.getCmplCounts() + construction.getCmplCount());
                seqInfo.setErrCounts(seqInfo.getErrCounts() + construction.getErrCount());
                seqInfoRepository.save(seqInfo);
                break;
            case APPROVING:
                // 进入审批流程
                // to do noting else
                break;
            case APPROVED:
                // 审批完成，计算工资
                // to do nothing else
                break;
            default:
                log.error("status error ! 工单状态设置有无，不应该出现:" + enumConstructStatus);
                return;
        }
        construction.setEnumConstructStatus(enumConstructStatus);
        constructionRepository.save(construction);
    }
}
