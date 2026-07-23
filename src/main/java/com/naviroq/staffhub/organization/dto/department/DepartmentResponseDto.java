

package com.naviroq.staffhub.organization.dto.department;
import jakarta.validation.constraints.*;


public record DepartmentResponseDto(

        @NotBlank
        String name,

        @NotBlank
        String code,

        String description

) {}