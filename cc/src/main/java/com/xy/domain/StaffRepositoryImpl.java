package com.xy.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class  StaffRepositoryImpl implements StaffRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<StaffSalary> getStaffSalary(int page, int size, String name, Date start, Date end) throws Exception {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        QStaff qStaff = QStaff.staff;
        QConstruction qConstruction = QConstruction.construction;
        QSeq qSeq = QSeq.seq;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qStaff.enumStaffStatus.eq(EnumStaffStatus.POSITIONING))
                .and(qConstruction.enumConstructStatus.eq(EnumConstructStatus.APPROVED));
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qStaff.staffName.like("%"+name+"%"))
                    .and(qStaff.enumStaffStatus.eq(EnumStaffStatus.POSITIONING));
        }
        if (start != null && end != null) {
            DateExpression<Date> exstart = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", start, "%Y-%m-%d %T");
            DateExpression<Date> exend = Expressions.dateTemplate(Date.class, "DATE_FORMAT({0}, {1})", end,  "%Y-%m-%d %T");
            booleanBuilder.and(qConstruction.sDate.between(exstart, exend));
        }

        NumberExpression<Float> sumCost = qSeq.seqCost.multiply(qConstruction.dstCount).sum();

        List<StaffSalary> staffList = jpaQueryFactory.selectFrom(qStaff)
                .leftJoin(qStaff.constructs, qConstruction)
                .leftJoin(qConstruction.seq, qSeq)
                .select(Projections.constructor(
                        StaffSalary.class, qStaff.staffName, sumCost))
                .where(booleanBuilder)
                .orderBy(sumCost.desc())
                .groupBy(qStaff.idStaff)
                .offset(page-1 >=0 ? (page-1) * size : 0)
                .limit(size)
                .fetch();

        Long total = jpaQueryFactory.selectFrom(qStaff)
                .leftJoin(qStaff.constructs, qConstruction)
                .leftJoin(qConstruction.seq, qSeq)
                .select(Projections.constructor(
                        StaffSalary.class, qStaff.staffName, sumCost))
                .where(booleanBuilder)
                .groupBy(qStaff.idStaff).fetchCount();

        Pageable pageable = new QPageRequest(page, size);

        return new PageImpl<StaffSalary>(staffList, pageable, total);
    }
}
