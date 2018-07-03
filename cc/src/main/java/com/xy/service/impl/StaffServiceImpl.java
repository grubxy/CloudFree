package com.xy.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xy.domain.*;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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


//        staffList = jpaQueryFactory.select(Projections.constructor(
//               StaffSalary.class, qStaff.staffName
//        )).from(qStaff)
//                .leftJoin(qStaff.constructs, qConstruction)
//        .leftJoin(qConstruction.seq, qSeq).fetch();
//        JPAExpressions.select(qStaff.staffName, qSeq.seqCost)
//                .from(qStaff)
//                .leftJoin(qStaff.constructs, qConstruction)
//                .leftJoin(qConstruction.seq, qSeq);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qStaff.staffName.like("%" + name + "%").and(qStaff.enumStaffStatus.eq(EnumStaffStatus.POSITIONING)));
        }
        if (start != null && end != null) {
            DateExpression<Date> exstart = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", start, "%Y-%m-%d %T");
            DateExpression<Date> exend = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", end,  "%Y-%m-%d %T");
            booleanBuilder.and(qConstruction.sDate.between(exstart, exend));
        }

//        Query query =  jpaQueryFactory.selectFrom(qStaff).createQuery();

        NumberExpression<Float> sumCost = qSeq.seqCost.multiply(qConstruction.dstCount).sum().as("sumCost");

        List<StaffSalary> staffList = jpaQueryFactory.selectFrom(qStaff)
                .leftJoin(qStaff.constructs, qConstruction)
                .leftJoin(qConstruction.seq, qSeq)
                .select(Projections.constructor(
                        StaffSalary.class, qStaff.staffName, sumCost))
                .where(booleanBuilder)
                .orderBy(sumCost.desc())
                .groupBy(qStaff.staffName, qStaff.idStaff)
                .offset(page-1 >=0 ? (page-1) * size : 0)
                .limit(size)
                .fetch();

        Long total = jpaQueryFactory.selectFrom(qStaff)
                .leftJoin(qStaff.constructs, qConstruction)
                .leftJoin(qConstruction.seq, qSeq)
                .select(Projections.constructor(
                        StaffSalary.class, qStaff.staffName, sumCost))
                .where(booleanBuilder)
                .orderBy(sumCost.desc())
                .groupBy(qStaff.staffName, qStaff.idStaff).fetchCount();

        Pageable pageable = new QPageRequest(page, size);

        return new PageImpl<StaffSalary>(staffList, pageable, total);
    }
}
