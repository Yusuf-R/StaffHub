package com.naviroq.staffhub.organization.dto.employee;

import com.naviroq.staffhub.common.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record CreateEmployeeRequest(
        String employeeCode,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        LocalDate hireDate,
        String phone,
        String address,
        UUID departmentId,
        UUID positionId,
        UUID managerId
) {}