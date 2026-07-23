

package com.naviroq.staffhub.organization.domain.department.dto;
import jakarta.validation.constraints.*;

import java.util.UUID;


public record DepartmentResponseDto(
        UUID id,
        String name,
        String code,
        String description
) {}