package com.xy.service.impl;

import com.xy.domain.Staff;
import com.xy.domain.StaffRepository;
import com.xy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public void addStaff(Staff staff) throws Exception {
        staffRepository.save(staff);
    }

    @Override
    public void delStaff(int id) throws Exception {
        staffRepository.delete(id);
    }
}
