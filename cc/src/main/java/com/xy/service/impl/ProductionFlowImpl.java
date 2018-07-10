package com.xy.service.impl;

import com.github.javafaker.Bool;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.xy.domain.*;
import com.xy.service.ProductionFlowService;
import com.xy.utils.AuthContext;
import com.xy.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeqRepository seqRepository;

    // 新增流程
    @Override
    public void addProductionFlow(ProductionFlow productionFlow) throws Exception {

        // 生产编号
        productionFlow.setIdProduction(String.valueOf(SnowFlake.getInstance().nextId()));
        productionFlow.setCmplCounts(0);
        productionFlow.setErrCounts(0);
        productionFlow.setDate(new Date());
        // 获取提单人姓名
        productionFlow.setOwner(userRepository.findByUsername(AuthContext.getUsername()).getOwner());

        // 保存flow
        ProductionFlow flow = productionFlowRepository.save(productionFlow);

        Product product = productRepository.findOne(productionFlow.getProduct().getIdProduct());

        for (Seq seq : product.getSeq()) {
            SeqInfo seqInfo = new SeqInfo();
            seqInfo.setCmplCounts(0);
            seqInfo.setErrCounts(0);
            seqInfo.setDoingCounts(0);
            // 如果是第一个工序 设置
            if (seq.getSeqIndex() == 1) {
                seqInfo.setDstCounts(productionFlow.getDstCounts());
            } else {
                seqInfo.setDstCounts(0);
            }
            seqInfo.setProductionFlow(flow);
            seqInfoRepository.save(seqInfo);

            seq.setSeqInfo(seqInfo);
            seqRepository.save(seq);
        }
    }

    // 删除流程
    @Override
    public void delProductionFlow(String id) throws Exception {
        productionFlowRepository.delete(id);
    }

    // 获取生产流程 分页
    @Override
    public Page<ProductionFlow> getAllProductionFlow(int page, int size, String id, String name) throws Exception {

        Long total = (size !=0)?size:productionFlowRepository.count();

        Pageable pageable = new PageRequest(page, total.intValue(), new Sort(Sort.Direction.DESC, "date"));

        QProductionFlow qProductionFlow = QProductionFlow.productionFlow;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(id)) {
            booleanBuilder.and(qProductionFlow.idProduction.like("%" + id + "%"));
        }
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qProductionFlow.product.ProductName.like("%" + name + "%"));
        }

        return productionFlowRepository.findAll(booleanBuilder, pageable);
    }

    // 获取生产流程的所有工序
    @Override
    public List<Seq> getAllSeqByFlowId(String id) throws Exception {
        QSeq qSeq = QSeq.seq;
        Pageable pageable = new QPageRequest(0, new Long(seqRepository.count()).intValue(),new QSort(qSeq.seqIndex.desc()));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSeq.seqInfo.productionFlow.idProduction.eq(id));
        return seqRepository.findAll(booleanBuilder, pageable).getContent();
    }

    // 获取生产流程的工序详情
    @Override
    public List<SeqInfo> getAllSeqInfoByFlowId(String id) throws Exception {
        QSeqInfo qSeqInfo = QSeqInfo.seqInfo;
        Pageable pageable = new QPageRequest(0,
                new Long(seqInfoRepository.count()).intValue(),
                new QSort(qSeqInfo.seq.seqIndex.asc()));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSeqInfo.productionFlow.idProduction.eq(id));
        return seqInfoRepository.findAll(booleanBuilder, pageable).getContent();
    }

    // 增加施工单
    @Override
    public void addConstructionByFlowId(String id, Construction construction) throws Exception {

        construction.setCmplCount(0);
        construction.setSDate(new Date());
        construction.setEnumConstructStatus(EnumConstructStatus.WAITING);
        construction.setIdConstruct(String.valueOf(SnowFlake.getInstance().nextId()));
        ProductionFlow productionFlow = productionFlowRepository.findOne(id);
        construction.setProduction(productionFlow);

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
        constructionRepository.save(construction);
    }

    // 获取某流程所有施工单
    @Override
    public List<Construction> getConstructionByFlowId(String id) throws Exception {
        QConstruction qConstruction = QConstruction.construction;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qConstruction.production.idProduction.eq(id));
        Pageable pageable = new QPageRequest(0,
                new Long(constructionRepository.count()).intValue(),
                new QSort(qConstruction.sDate.desc()));
        return constructionRepository.findAll(booleanBuilder, pageable).getContent();
    }

    // 根据状态获取工单
    @Override
    public Page<Construction> getConstructionByStatus(int page, int size, String status, String id, String name, String staff, Date start, Date end) throws Exception {
        Long total = (size !=0)?size:constructionRepository.count();
        Pageable pageable = new PageRequest(page, total.intValue(), new Sort(Sort.Direction.DESC, "sDate"));
        QConstruction qConstruction = QConstruction.construction;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(id)) {
            booleanBuilder.and(qConstruction.idConstruct.like("%"+id+"%"));
        }
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qConstruction.production.product.ProductName.like("%" + name + "%"));
        }
        if (!StringUtils.isEmpty(staff)) {
            booleanBuilder.and(qConstruction.staff.staffName.like("%" + staff + "%"));
        }

        if (!StringUtils.isEmpty(status)) {
            if (Integer.valueOf(status) != EnumConstructStatus.ALL.ordinal()) {
                if (Integer.valueOf(status) != EnumConstructStatus.SCHEDULE.ordinal()) {
                    booleanBuilder.and(qConstruction.enumConstructStatus.eq(EnumConstructStatus.values()[Integer.valueOf(status)]));
                } else {
                    booleanBuilder.andAnyOf(qConstruction.enumConstructStatus
                            .eq(EnumConstructStatus.values()[EnumConstructStatus.WAITING.ordinal()]),
                                    qConstruction.enumConstructStatus.eq(EnumConstructStatus.values()[EnumConstructStatus.WORKING.ordinal()]));
                }
            }
        }

        if (start!= null && end != null) {
            DateExpression<Date> exstart = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", start, "%Y-%m-%d %T");
            DateExpression<Date> exend = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", end,  "%Y-%m-%d %T");
//            DateExpression<Date> exstart = Expressions.dateTemplate(Date.class, "CAST({0} as DATE)", start);
//            DateExpression<Date> exend = Expressions.dateTemplate(Date.class, "CAST({0} as DATE)", end);
            booleanBuilder.and(qConstruction.sDate.between(exstart, exend));
        }

        return constructionRepository.findAll(booleanBuilder, pageable);
    }

    // 根据工单获取对应工序详情
    private SeqInfo getSeqInfoFromConstruction(Construction construction) throws Exception {
        QSeqInfo qSeqInfo = QSeqInfo.seqInfo;
        QConstruction qConstruction = QConstruction.construction;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSeqInfo.seq.idSeq.eq(construction.getSeq().getIdSeq()));
        return seqInfoRepository.findOne(booleanBuilder);
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
                    boolean bContain = false;
                    for (Origin tmp: house.getOrigins()) {
                        // 如果原料已经存在
                        if (tmp.getMaterial().getIdMaterial() == origin.getMaterial().getIdMaterial()) {
                            tmp.setCounts(tmp.getCounts() + origin.getCounts());
                            bContain = true;
                            break;
                        }
                    }
                    if (!bContain) {
                        house.getOrigins().add(origin);
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
