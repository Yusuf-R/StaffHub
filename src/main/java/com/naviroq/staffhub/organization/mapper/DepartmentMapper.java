package com.naviroq.staffhub.organization.mapper;

import com.naviroq.staffhub.organization.domain.department.CreateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.UpdateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.dto.CreateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.department.dto.DepartmentResponseDto;
import com.naviroq.staffhub.organization.domain.department.dto.UpdateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.entity.Department;

public interface DepartmentMapper {
    CreateDepartmentCommand fromDto (CreateDepartmentRequest dto);
    UpdateDepartmentCommand fromDto (UpdateDepartmentRequest dto);
    DepartmentResponseDto toDto (Department department);
}