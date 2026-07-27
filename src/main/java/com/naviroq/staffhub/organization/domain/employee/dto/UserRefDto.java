package com.naviroq.staffhub.organization.domain.employee.dto;

import com.naviroq.staffhub.common.enums.RoleCode;

import java.util.UUID;

public record UserRefDto(
        UUID id,
        String username,
        String email,
        RoleCode roleCode
) {}