package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.organization.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {}