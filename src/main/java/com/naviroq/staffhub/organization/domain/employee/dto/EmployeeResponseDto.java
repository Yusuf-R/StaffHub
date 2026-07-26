package com.naviroq.staffhub.organization.domain.employee.dto;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.UserStatus;

import java.util.UUID;

public record EmployeeResponseDto(
        UUID id,
        String employeeCode,
        String firstName,
        String lastName,
        String bio,
        String dateOfBirth,
        String phone,
        String address,
        String department,
        String position,
        String profilePictureUrl,
        EmploymentType employmentType,
        UserStatus userStatus,
        EmploymentStatus employmentStatus,
        UserRefDto user,
        UUID departmentId,
        UUID positionId
) {
}