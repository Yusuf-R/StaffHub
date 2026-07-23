package com.naviroq.staffhub.organization.mapper;

import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.CreateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;
import com.naviroq.staffhub.organization.domain.employee.dto.UpdateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.entity.Employee;

public interface EmployeeMapper {
    CreateEmployeeCommand fromDto (CreateEmployeeRequest dto);
    UpdateEmployeeCommand fromDto (UpdateEmployeeRequest dto);
    EmployeeResponseDto toDto (Employee employee);
}