package com.naviroq.staffhub.organization.domain.employee.dto;

import com.naviroq.staffhub.common.enums.EmploymentStatus;

import java.util.UUID;

public record EmployeeResponseDto(
        UUID id,
        String employeeCode,
        String firstName,
        String lastName,
        String department,
        String position,
        EmploymentStatus status,
        UserRefDto user
) {}