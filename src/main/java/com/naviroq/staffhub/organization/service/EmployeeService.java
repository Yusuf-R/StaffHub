package com.naviroq.staffhub.organization.service;

import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    Employee createEmployee (CreateEmployeeCommand command);
    Employee updateEmployee(UUID employeeId, UpdateEmployeeCommand command);
    Employee getEmployeeById(UUID employeeId);
    List <Employee> listOfEmployee();
    void deleteEmployee (UUID employeeId);
}