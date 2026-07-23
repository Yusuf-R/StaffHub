package com.naviroq.staffhub.organization.domain.employee.dto;

import com.naviroq.staffhub.common.enums.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record OnboardStaffRequest(

        // ---- EMPLOYEE FIELDS ----
        @NotBlank(message = "Employee code cannot be blank")
        @Length(min = 3, max = 20)
        String employeeCode,

        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        @Length(max = 20)
        String phone,

        @Length(max = 255)
        String address,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Employment type is required")
        EmploymentType employmentType,

        LocalDate dateOfBirth,

        @NotNull(message = "Hire date is required")
        LocalDate hireDate,

        @Length(max = 500)
        String bio,

        String profilePictureUrl,

        @NotNull(message = "Status is required")
        EmploymentStatus status,

        @NotNull(message = "Department ID is required")
        UUID departmentId,

        @NotNull(message = "Position ID is required")
        UUID positionId,

        UUID managerId,

        // ---- USER FIELDS ----
        @NotBlank(message = "Username cannot be blank")
        @Length(min = 3, max = 50)
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Length(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotNull(message = "Role is required")
        RoleCode roleCode

) {
}