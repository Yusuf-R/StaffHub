package com.naviroq.staffhub.organization.domain.employee.dto;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record CreateEmployeeRequest(

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

        EmploymentType employmentType,  // ✅ No @NotNull. FE can omit it.

        EmploymentStatus status,         // ✅ No @NotNull. FE can omit it

        @NotNull(message = "Department is required")
        UUID departmentId,

        @NotNull(message = "Position is required")
        UUID positionId,

        UUID managerId

) {}