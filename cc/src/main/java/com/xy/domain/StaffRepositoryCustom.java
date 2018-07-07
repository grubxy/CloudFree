package com.xy.domain;

import org.springframework.data.domain.Page;

import java.util.Date;

public interface StaffRepositoryCustom {
    Page<StaffSalary> getStaffSalary(int page, int size, String name, Date start, Date end) throws Exception;
}
