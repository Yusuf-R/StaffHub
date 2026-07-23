package com.naviroq.staffhub.organization.domain.position.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdatePositionRequest(

        @NotBlank(message = "Position title cannot be blank")
        @Length(min = 2, max = 100, message = "Position title must be between 2 and 100 characters")
        String title,

        @Length(max = 500, message = "Description cannot exceed 500 characters")
        String description

) {}
