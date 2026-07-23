package com.naviroq.staffhub.organization.domain.position.dto;

import java.util.UUID;

public record PositionResponseDto(
        UUID id,
        String title,
        String description
) {}