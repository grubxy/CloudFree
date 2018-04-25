package com.xy.service.impl;

import com.xy.domain.EnumStaffStatus;
import com.xy.domain.Staff;
import com.xy.domain.StaffRepository;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    public List<Staff> getStaffListByStatus(int page, int size, int status) throws Exception {
        Long total = (size!=0)?size:staffRepository.count();
        PageRequest pageRequest = new PageRequest(page, total.intValue());
        return staffRepository.findStaffByEnumStaffStatus(EnumStaffStatus.values()[status], pageRequest).getContent();
    }
}
