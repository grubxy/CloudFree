package com.xy.service.impl;

import com.xy.domain.*;
import com.xy.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class BaseDataServiceImpl implements BaseDataService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SeqRepository seqRepository;

    // 新增or更新产品
    @Override
    public void addProduct(Product product) throws Exception {
        productRepository.save(product);
    }

    // 删除产品
    @Override
    public void delProduct(int id) throws Exception {
        productRepository.delete(id);
    }

    // 新增or更新某个产品的工序
    @Override
    public void addSeqByProductId(int id, Seq seq) throws Exception {
        Product product = productRepository.findOne(id);
        Material material = new Material();
        material.setName(product.getProductName() + "_" + seq.getSeqName());
        seq.setMaterial(material);
        if (product != null) {
            if (product.getSeq().size() != 0) {
                product.getSeq().add(seq);
            } else {
                Set<Seq> seqSet = new HashSet<Seq>();
                seqSet.add(seq);
                product.setSeq(seqSet);
            }
            productRepository.save(product);

        } else {
            throw new UserException(ErrorCode.PRODUCT_ID_ERROR.getCode(), ErrorCode.PRODUCT_ID_ERROR.getMsg());
        }
    }

    // 删除某个工序
    @Override
    public void delSeqById(int id) throws Exception{
        seqRepository.delete(id);
    }

    // 给某个工序添加默认员工
    @Override
    public void addStaffBySeqId(int id, Staff staff) throws Exception {
        Seq seq = seqRepository.findOne(id);
        if (seq != null) {
            if (seq.getStaffs().size() != 0) {
                seq.getStaffs().add(staff);
            } else {
                Set<Staff> staffSet = new HashSet<Staff>();
                staffSet.add(staff);
                seq.setStaffs(staffSet);
            }
            seqRepository.save(seq);

        } else {
            throw new UserException(ErrorCode.SEQ_ID_ERROR.getCode(), ErrorCode.SEQ_ID_ERROR.getMsg());
        }
    }

    // 获取所有基础数据信息
    @Override
    public Page<Product> getAllProduct(int page, int size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        return productRepository.findAll(pageRequest);
    }
}
