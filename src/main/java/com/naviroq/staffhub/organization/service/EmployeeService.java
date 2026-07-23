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
    Employee paginatedGetAllEmployees(int page, int size, String sortBy);
    List <Employee> listOfEmployee();

    void deleteEmployee (UUID employeeId);

    List<Employee> findByDepartmentAndStatus(String department, String status);

    List<Employee> findByDepartment(String department);

    List<Employee> findByStatus(String status);

    List<Employee> getAllEmployees();
}