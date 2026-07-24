package com.naviroq.staffhub.identity.domain.dto;

import com.naviroq.staffhub.common.enums.RoleCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

// the shape of the object that the must be enforced when the client PUT
public record UpdateUserRequestDto(
        @NotBlank(message = ERR_USERNAME_BLANK)
        @Length(max=100, message = ERR_USERNAME_LENGTH)
        String username,

        @NotNull(message = ERR_ROLE_CODE)
        RoleCode roleCode,

        @Length(min = 6, max = 255, message = ERR_PASSWORD_LENGTH)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = ERR_PASSWORD_PATTERN
        )
        String password
) {
    private static final String ERR_USERNAME_BLANK = "Username can not be blank";
    private static final String ERR_USERNAME_LENGTH = "Username should be between 1 - 255 characters";
    private static final String ERR_PASSWORD_LENGTH = "Password must be at least 6 characters";
    private static final String ERR_ROLE_CODE = "Role must either be {ADMIN, STAFF, SUPER_ADMIN";
    private static final String ERR_PASSWORD_PATTERN = "Password must contain uppercase, number, and special character";
}