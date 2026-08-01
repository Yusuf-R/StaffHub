package com.naviroq.staffhub.workflow.domain.dto;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.Gender;
import com.naviroq.staffhub.common.enums.RoleCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record CreateOnboardingWorkflowRequest(

        // ===============================
        // Employee Information
        // ===============================

        @NotBlank(message = "First name cannot be blank")
        @Length(max = 100, message = "First name cannot exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Length(max = 100, message = "Last name cannot exceed 100 characters")
        String lastName,

        @NotNull(message = "Gender is required")
        Gender gender,

        LocalDate dateOfBirth,

        @NotNull(message = "Hire date is required")
        LocalDate hireDate,

        @Length(max = 20, message = "Phone number cannot exceed 20 characters")
        String phone,

        @Length(max = 255, message = "Address cannot exceed 255 characters")
        String address,

        String bio,

        String profilePictureUrl,

        EmploymentType employmentType,

        EmploymentStatus employmentStatus,

        @NotNull(message = "Department is required")
        UUID departmentId,

        @NotNull(message = "Position is required")
        UUID positionId,

        UUID managerId,

        // ===============================
        // User Account Information
        // ===============================

        @NotBlank(message = "Username can not be blank")
        @Length(min = 3, max = 100, message = "Username to long, 3-100 is the acceptable range")
        String username,

        @NotBlank(message = "Work email is required")
        @Email(message = "Invalid work email")
        String workEmail,

        @NotNull(message = "Role is required")
        RoleCode roleCode

) {
}