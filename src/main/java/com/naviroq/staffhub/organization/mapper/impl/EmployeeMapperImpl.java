package com.naviroq.staffhub.organization.mapper.impl;

import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.CreateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;
import com.naviroq.staffhub.organization.domain.employee.dto.UpdateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public CreateEmployeeCommand fromDto(CreateEmployeeRequest dto) {
        return new CreateEmployeeCommand(
                dto.employeeCode(),
                dto.firstName(),
                dto.lastName(),
                dto.gender(),
                dto.dateOfBirth(),
                dto.hireDate(),
                dto.phone(),
                dto.address(),
                dto.departmentId(),
                dto.positionId(),
                dto.managerId()
        );
    }

    @Override
    public UpdateEmployeeCommand fromDto(UpdateEmployeeRequest dto) {
        return new UpdateEmployeeCommand(
                dto.firstName(),
                dto.lastName(),
                dto.gender(),
                dto.dateOfBirth(),
                dto.phone(),
                dto.address(),
                dto.bio(),
                dto.status(),
                dto.departmentId(),
                dto.positionId(),
                dto.managerId()
        );
    }

    @Override
    public EmployeeResponseDto toDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment().getName(),
                employee.getPosition().getTitle(),
                employee.getStatus()
        );
    }
}
