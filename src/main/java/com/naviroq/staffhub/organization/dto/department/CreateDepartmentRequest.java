package com.naviroq.staffhub.organization.dto.department;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record CreateDepartmentRequest(

        @NotBlank
        String name,

        @NotBlank
        String code,

        String description

) {}