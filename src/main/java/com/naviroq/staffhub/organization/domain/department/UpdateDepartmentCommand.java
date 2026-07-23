package com.naviroq.staffhub.organization.domain.department;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateDepartmentCommand(String name, String code, String description) {
}
