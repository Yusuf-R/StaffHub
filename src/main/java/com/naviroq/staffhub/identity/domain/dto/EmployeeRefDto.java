package com.naviroq.staffhub.identity.domain.dto;

import java.util.UUID;

public record EmployeeRefDto(
        UUID id,
        String employeeCode,
        String fullName
) {}