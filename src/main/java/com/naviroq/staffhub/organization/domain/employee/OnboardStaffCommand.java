
package com.naviroq.staffhub.organization.domain.employee;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.Gender;
import com.naviroq.staffhub.common.enums.RoleCode;

import java.time.LocalDate;
import java.util.UUID;

public record OnboardStaffCommand (
        // Employee Section
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        LocalDate hireDate,
        String phone,
        String address,
        String bio,
        String profilePictureUrl,
        EmploymentType employmentType,
        EmploymentStatus status,
        UUID departmentId,
        UUID positionId,
        UUID managerId,
        // User Section
        String username,
        String email,
        RoleCode roleCode,
        String password
) {
}