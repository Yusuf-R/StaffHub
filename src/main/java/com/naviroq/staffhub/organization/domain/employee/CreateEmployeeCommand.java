package com.naviroq.staffhub.organization.domain.employee;

import com.naviroq.staffhub.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record CreateEmployeeCommand(
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
) {
}