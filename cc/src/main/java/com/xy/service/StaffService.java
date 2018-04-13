package com.xy.service;

import com.xy.domain.Staff;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {
    void addStaff(Staff staff) throws Exception;

    void delStaff(int id) throws Exception;

    void setStaffStatus(int id, int status) throws Exception;

    List<Staff> getStaffListByStatus(int status) throws Exception;

    Page<Staff> getStaffByStatus(int status, int page, int size) throws Exception;
}
