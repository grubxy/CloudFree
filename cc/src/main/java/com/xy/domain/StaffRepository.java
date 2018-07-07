package com.xy.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Integer>,QueryDslPredicateExecutor<Staff>, StaffRepositoryCustom {
    Page<Staff> findStaffByEnumStaffStatus(EnumStaffStatus enumStaffStatus, Pageable pageable);

    List<Staff> findStaffByEnumStaffStatus(EnumStaffStatus enumStaffStatus);
}
