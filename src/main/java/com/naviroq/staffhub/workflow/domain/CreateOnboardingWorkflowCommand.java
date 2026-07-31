package com.naviroq.staffhub.workflow.domain;

import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.Gender;
import com.naviroq.staffhub.common.enums.RoleCode;

import java.time.LocalDate;
import java.util.UUID;

public record CreateOnboardingWorkflowCommand(

        // ===============================
        // Employee Information
        // ===============================

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

        UUID departmentId,

        UUID positionId,

        UUID managerId,

        // ===============================
        // User Information
        // ===============================

        String workEmail,

        RoleCode roleCode

) {
}