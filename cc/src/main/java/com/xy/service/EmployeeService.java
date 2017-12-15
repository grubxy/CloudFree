package com.xy.service;

import com.xy.dao.pay.Employee;
import com.xy.model.EmployeeSelect;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee em);

    void delEmployee(Employee em);

    Page<Employee> selectEmployee(int page, int size);

    List<Employee> findAll();
}
