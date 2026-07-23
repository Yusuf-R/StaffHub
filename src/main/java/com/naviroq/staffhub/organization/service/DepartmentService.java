package com.naviroq.staffhub.organization.service;

import com.naviroq.staffhub.organization.domain.department.CreateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.UpdateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.entity.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    Department createDepartment(CreateDepartmentCommand command);

    Department getDepartmentById(UUID departmentId);

    List<Department> listDepartments();

    Department updateDepartment(UUID departmentId, UpdateDepartmentCommand command);

    void deleteDepartment(UUID departmentId);

}