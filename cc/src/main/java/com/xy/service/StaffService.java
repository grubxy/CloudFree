package com.xy.service;

import com.xy.domain.SaffSalary;
import com.xy.domain.Staff;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface StaffService {
    void addStaff(Staff staff) throws Exception;

    void delStaff(int id) throws Exception;

    void setStaffStatus(int id, int status) throws Exception;

    Page<Staff> getStaffListByStatus(int page, int size, String status, String name) throws Exception;

    Page<SaffSalary> getStaffSalaryByName(int page, int size, String name, Date start, Date end) throws Exception;
}
