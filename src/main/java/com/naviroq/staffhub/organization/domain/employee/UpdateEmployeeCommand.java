package com.naviroq.staffhub.organization.domain.employee;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeCommand(
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        String phone,
        String address,
        String bio,
        EmploymentStatus status,
        UUID departmentId,
        UUID positionId,
        UUID managerId
) {
}