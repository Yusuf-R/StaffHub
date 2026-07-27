package com.naviroq.staffhub.identity.domain.dto;

import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;

public record LoginResponseDto (
        String accessToken,
        Long expiresIn,
        EmployeeResponseDto employee
) {
}
