package com.xy.service;

import com.xy.domain.Staff;

public interface StaffService {
    void addStaff(Staff staff) throws Exception;

    void delStaff(int id) throws Exception;

    void setStaffStatus(int id, int status) throws Exception;
}
