package com.naviroq.staffhub.organization.domain.employee.dto;

import java.util.UUID;

public record UserRefDto(
        UUID id,
        String username,
        String email
) {}