package com.xy.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xy.domain.*;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private EntityManager entityManager;

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
    public Page<SaffSalary> getStaffSalaryByName(int page, int size, String name, Date start, Date end) throws Exception {

        JPAQuery<?> query=new JPAQuery<>(entityManager);

        QStaff qStaff = QStaff.staff;
        QConstruction qConstruction = QConstruction.construction;



        query.select(qStaff.staffName)
                .leftJoin(qStaff.constructs, qConstruction)
                .fetchAll();

    }
}
