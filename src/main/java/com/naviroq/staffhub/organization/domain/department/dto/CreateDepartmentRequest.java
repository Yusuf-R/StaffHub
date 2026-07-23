package com.naviroq.staffhub.organization.domain.department.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateDepartmentRequest(

        @NotBlank(message = "Department name cannot be blank")
        @Length(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Department code cannot be blank")
        @Length(min = 2, max = 10, message = "Department code must be between 2 and 10 characters")
        String code,

        @Length(max = 500, message = "Description cannot exceed 500 characters")
        String description

) {}