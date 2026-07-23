package com.naviroq.staffhub.organization.mapper.impl;

import com.naviroq.staffhub.organization.domain.department.CreateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.UpdateDepartmentCommand;
import com.naviroq.staffhub.organization.domain.department.dto.CreateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.department.dto.DepartmentResponseDto;
import com.naviroq.staffhub.organization.domain.department.dto.UpdateDepartmentRequest;
import com.naviroq.staffhub.organization.domain.entity.Department;
import com.naviroq.staffhub.organization.mapper.DepartmentMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapperImpl implements DepartmentMapper {
    @Override
    public CreateDepartmentCommand fromDto(CreateDepartmentRequest dto) {
        return new CreateDepartmentCommand(dto.name(), dto.code(), dto.description());
    }

    @Override
    public UpdateDepartmentCommand fromDto(UpdateDepartmentRequest dto) {
        return new UpdateDepartmentCommand(dto.name(), dto.code(), dto.description());
    }

    @Override
    public DepartmentResponseDto toDto(Department department) {
        return new DepartmentResponseDto(department.getName(), department.getCode(), department.getDescription());
    }
}
