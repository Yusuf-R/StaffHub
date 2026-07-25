package com.naviroq.staffhub.organization.service;

import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    // --- Write operations ---
    Employee createEmployee(CreateEmployeeCommand command);
    Employee updateEmployee(UUID employeeId, UpdateEmployeeCommand command);

    // --- Read operations (ALL should load department, position, user) ---
    Employee getEmployeeById(UUID employeeId);
    List<Employee> listOfEmployee();
    List<Employee> getAllEmployees();
    List<Employee> findByDepartment(String department);
    List<Employee> findByStatus(String status);
    List<Employee> findByDepartmentAndStatus(String department, String status);

    // 👇 UPDATED: Delete with a reason
    void deleteEmployee(UUID employeeId, String reason);

    // 👇 NEW: Restore method
    void restoreEmployee(UUID employeeId);

    // --- Paginated (later) ---
    Page<Employee> paginatedGetAllEmployees(int page, int size, String sortBy);
}