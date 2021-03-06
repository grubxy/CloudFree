package com.xy.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.xy.domain.*;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class StaffServiceImpl implements StaffService {

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
        QStaff qStaff = QStaff.staff;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qStaff.staffName.like("%"+name+"%"));
        }
        if (!StringUtils.isEmpty(status)) {
            booleanBuilder.and(qStaff.enumStaffStatus.eq(EnumStaffStatus.values()[Integer.valueOf(status)]));
        }
        Long total = (size!=0)?size:staffRepository.count(booleanBuilder);
        if (total == 0) {
            throw new UserException(ErrorCode.STAFF_NO_ERROR.getCode(), ErrorCode.STAFF_NO_ERROR.getMsg());
        }
        Pageable pageable = new PageRequest(page, total.intValue(), new Sort(Sort.Direction.DESC, "idStaff"));
        return staffRepository.findAll(booleanBuilder, pageable);
    }

    @Override
    @Transactional
    public Page<StaffSalary> getStaffSalaryByName(int page, int size, String name, Date start, Date end) throws Exception {
        return staffRepository.getStaffSalary(page, size, name ,start, end);
    }
}
