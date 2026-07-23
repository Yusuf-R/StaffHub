package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.organization.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {}