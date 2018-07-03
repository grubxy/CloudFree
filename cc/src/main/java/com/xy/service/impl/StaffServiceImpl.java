package com.xy.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xy.domain.*;
import com.xy.service.StaffService;
import javafx.beans.binding.NumberExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public void addStaff(Staff staff) throws Exception {
        staff.setEnumStaffStatus(EnumStaffStatus.POSITIONING);
        staffRepository.save(staff);
    }

    @Override
    public void delStaff(int id) throws Exception {
        staffRepository.delete(id);
    }

    @Override
    public void setStaffStatus(int id, int status) throws Exception {
        EnumStaffStatus enumStaffStatus = EnumStaffStatus.values()[status];
        Staff staff = staffRepository.findOne(id);
        staff.setEnumStaffStatus(enumStaffStatus);
        staffRepository.save(staff);
    }

    @Override
    public Page<Staff> getStaffListByStatus(int page, int size, String status, String name) throws Exception {
        Long total = (size!=0)?size:staffRepository.count();
        Pageable pageable = new PageRequest(page, total.intValue(), new Sort(Sort.Direction.DESC, "idStaff"));
        QStaff qStaff = QStaff.staff;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qStaff.staffName.like("%"+name+"%"));
        }
        if (!StringUtils.isEmpty(status)) {
            booleanBuilder.and(qStaff.enumStaffStatus.eq(EnumStaffStatus.values()[Integer.valueOf(status)]));
        }
        return staffRepository.findAll(booleanBuilder, pageable);
    }

    @Override
    public Page<StaffSalary> getStaffSalaryByName(int page, int size, String name, Date start, Date end) throws Exception {

        jpaQueryFactory = new JPAQueryFactory(entityManager);


        QStaff qStaff = QStaff.staff;
        QConstruction qConstruction = QConstruction.construction;
        QSeq qSeq = QSeq.seq;


        List<StaffSalary> staffList = new ArrayList<>();


//        staffList = jpaQueryFactory.select(Projections.constructor(
//               StaffSalary.class, qStaff.staffName
//        )).from(qStaff)
//                .leftJoin(qStaff.constructs, qConstruction)
//        .leftJoin(qConstruction.seq, qSeq).fetch();

        staffList = jpaQueryFactory.selectFrom(qStaff)
                .leftJoin(qStaff.constructs, qConstruction)
                .leftJoin(qConstruction.seq, qSeq)
                .select(Projections.constructor(
                        StaffSalary.class, qStaff.staffName, qSeq.seqCost.multiply(qConstruction.dstCount).sum().as("sumCost")))
                .groupBy(qStaff.staffName, qStaff.idStaff)
                .fetch();
//        JPAExpressions.select(qStaff.staffName, qSeq.seqCost)
//                .from(qStaff)
//                .leftJoin(qStaff.constructs, qConstruction)
//                .leftJoin(qConstruction.seq, qSeq);


        return null;
    }
}
