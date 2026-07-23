

package com.naviroq.staffhub.organization.domain.department.dto;
import jakarta.validation.constraints.*;


public record DepartmentResponseDto(

        @NotBlank
        String name,

        @NotBlank
        String code,

        String description

) {}