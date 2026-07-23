package com.naviroq.staffhub.organization.domain.position;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdatePositionCommand(

        String title,
        String description

) {}
