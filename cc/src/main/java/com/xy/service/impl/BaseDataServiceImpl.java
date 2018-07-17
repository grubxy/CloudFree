package com.xy.service.impl;

import com.github.javafaker.Bool;
import com.querydsl.core.BooleanBuilder;
import com.xy.domain.*;
import com.xy.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BaseDataServiceImpl implements BaseDataService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SeqRepository seqRepository;

    @Autowired
    private StaffRepository staffRepository;


    // 新增or更新产品
    @Override
    public void addProduct(Product product) throws Exception {
        productRepository.save(product);
    }

    // 删除产品
    @Override
    public void delProduct(int id) throws Exception {
        Product product = productRepository.findOne(id);
        // 删除工序与默认员工的关联关系
        for (Seq seq: product.getSeq()) {
            seq.getStaffs().removeAll(seq.getStaffs());
            seqRepository.save(seq);
        }
        product.getSeq().removeAll(product.getSeq());
        productRepository.save(product);
        productRepository.delete(id);
    }

    // 新增or更新某个产品的工序
    @Override
    public void addSeqByProductId(int id, Seq seq) throws Exception {

        Product product = productRepository.findOne(id);
        if (product == null) {
            throw new UserException(ErrorCode.PRODUCT_ID_ERROR.getCode(), ErrorCode.PRODUCT_ID_ERROR.getMsg());
        }

        // 设置源材料
        if (product.getSeq().size() == 0) {
            seq.setSeqIndex(1);
            Material material = new Material();
            material.setName(product.getProductName() + "_原始材料");
            seq.setSrcMaterial(material);
        } else {
            seq.setSeqIndex(product.getSeq().size() + 1);
            int size = product.getSeq().size();
            for (Seq tmp:product.getSeq()) {
                if (tmp.getSeqIndex() == size) {
                    seq.setSrcMaterial(tmp.getDstMaterial());
                }
            }
        }

        // 设置生成材料
        Material material = new Material();
        material.setName(product.getProductName() + "_" + seq.getSeqName());
        seq.setDstMaterial(material);

        seq.setProduct(product);
        seqRepository.save(seq);
    }

    // 删除产品工序
    @Override
    public void delSeqById(int id, int idSeq) throws Exception{
        Product product = productRepository.findOne(id);
        if (product == null) {
            throw new UserException(ErrorCode.PRODUCT_ID_ERROR.getCode(), ErrorCode.PRODUCT_ID_ERROR.getMsg());
        }
        Seq seq = seqRepository.findOne(idSeq);
        if (seq == null) {
            throw new UserException(ErrorCode.SEQ_ID_ERROR.getCode(), ErrorCode.SEQ_ID_ERROR.getMsg());
        }
        // 删除工序默认员工
        seq.getStaffs().removeAll(seq.getStaffs());
        seqRepository.save(seq);
        // 删除工序
        product.getSeq().remove(seq);
        productRepository.save(product);
    }

    // 获取产品的工序信息
    @Override
    public List<Seq> getSeqListByProductId(int id, int page, int size) throws Exception {
        QSeq qSeq = QSeq.seq;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSeq.product.idProduct.eq(id));
        Long total = (size!=0)?size:seqRepository.count(booleanBuilder);
        if (total == 0) {
            // 返回空
            return new ArrayList<>();
        }
        Pageable pageable = new QPageRequest(0, total.intValue(), new QSort(qSeq.seqIndex.asc()));
        return seqRepository.findAll(booleanBuilder, pageable).getContent();
    }

    // 给某个工序添加默认员工
    @Override
    public void addStaffBySeqId(int id, Staff staff) throws Exception {
        Seq seq = seqRepository.findOne(id);
        if (seq == null) {
            throw new UserException(ErrorCode.SEQ_ID_ERROR.getCode(), ErrorCode.SEQ_ID_ERROR.getMsg());
        }

        if (seq.getStaffs().size() != 0) {
            seq.getStaffs().add(staff);
        } else {
            List<Staff> staffSet = new ArrayList<>();
            staffSet.add(staff);
            seq.setStaffs(staffSet);
        }
        seqRepository.save(seq);
    }

    // 获取工序默认员工
    @Override
    public List<Staff> getStaffBySeqId(int id, int page, int size) throws Exception {
        QStaff qStaff = QStaff.staff;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qStaff.seqs.any().idSeq.eq(id));
        Long total = staffRepository.count(booleanBuilder);
        if (total == 0) {
            return new ArrayList<>();
        }
        Pageable pageable = new QPageRequest(page, total.intValue(),
                new QSort(qStaff.staffName.asc()));

        return staffRepository.findAll(booleanBuilder, pageable).getContent();
    }

    @Override
    public void delStaffBySeqId(int id, int idStaff) throws Exception {
        Seq seq = seqRepository.findOne(id);
        if (seq == null) {
            throw new UserException(ErrorCode.SEQ_ID_ERROR.getCode(), ErrorCode.SEQ_ID_ERROR.getMsg());
        }
        Staff staff = staffRepository.findOne(idStaff);
        if (staff == null) {
            throw new UserException(ErrorCode.STAFF_NO_ERROR.getCode(), ErrorCode.STAFF_NO_ERROR.getMsg());
        }
        seq.getStaffs().remove(staff);
        seqRepository.save(seq);
    }
    // 获取所有基础数据信息
    @Override
    public Page<Product> getAllProduct(int page, int size, String name) throws Exception {

        QProduct qProduct = QProduct.product;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qProduct.ProductName.like("%" + name + "%"));
        }

        Long total = (size != 0)?size:productRepository.count(booleanBuilder);
        if (total == 0) {
            throw new UserException(ErrorCode.PRODUCT_NO.getCode(), ErrorCode.PRODUCT_NO.getMsg());
        }
        Pageable pageable = new PageRequest(page, total.intValue(), new Sort(Sort.Direction.DESC, "idProduct"));
        return productRepository.findAll(booleanBuilder, pageable);
    }
}
