package com.naviroq.staffhub.organization.dto.position;

import java.util.UUID;

public record PositionResponseDto(

        UUID id,
        String title,
        String description

) {}