package com.naviroq.staffhub.organization.dto.employee;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeRequest(

        @NotBlank(message = "First name cannot be blank")
        @Length(max = 100)
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Length(max = 100)
        String lastName,

        @NotNull(message = "Gender is required")
        Gender gender,

        LocalDate dateOfBirth,

        @Length(max = 20)
        String phone,

        @Length(max = 255)
        String address,

        String bio,

        @NotNull(message = "Employment status is required")
        EmploymentStatus status,

        @NotNull(message = "Department is required")
        UUID departmentId,

        @NotNull(message = "Position is required")
        UUID positionId,

        UUID managerId

) {}