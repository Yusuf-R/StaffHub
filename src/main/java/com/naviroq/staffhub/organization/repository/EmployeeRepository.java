package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    // Spring Data JPA auto-implements these based on method name
    List<Employee> findByDepartment_Name(String departmentName);

    List<Employee> findByStatus(EmploymentStatus status);

    List<Employee> findByDepartment_NameAndStatus(String departmentName, EmploymentStatus status);
}