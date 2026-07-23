package com.naviroq.staffhub.identity.domain.dto;

import com.naviroq.staffhub.common.enums.RoleCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

// the shape of the object that the must be enforced when the client PUT
public record UpdateUserRequestDto(
        @NotBlank(message = ERR_USERNAME_BLANK)
        @Length(max=100, message = ERR_USERNAME_LENGTH)
        @NotNull(message = ERR_USERNAME_BLANK)
        String username,

        @NotNull(message = ERR_ROLE_CODE)
        @NotBlank(message = ERR_ROLE_CODE)
        RoleCode roleCode,

        @NotNull(message = ERR_PASSWORD)
        @NotBlank(message = ERR_PASSWORD)
        String password
) {
    private static final String ERR_USERNAME_BLANK = "Username can not be blank";
    private static final String ERR_USERNAME_LENGTH = "Username should be between 1 - 255 characters";
    private static final String ERR_EMAIL_BLANK = "Email Address can not blank";
    private static final String ERR_ROLE_CODE = "Role must either be {ADMIN, STAFF, SUPER_ADMIN";
    private static final String ERR_PASSWORD = "Password can not be blank";
}