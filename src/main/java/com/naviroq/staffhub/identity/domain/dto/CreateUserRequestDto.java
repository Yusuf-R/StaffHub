package com.naviroq.staffhub.identity.domain.dto;

import com.naviroq.staffhub.common.enums.RoleCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

// the shape of the object to be enforced when the client POST
public record CreateUserRequestDto(
        UUID employeeId,

        @NotBlank(message = ERR_USERNAME_BLANK)
        @Length(min = 3, max = 100, message = ERR_USERNAME_LENGTH)
        String username,

        @NotBlank(message = ERR_EMAIL_BLANK)
        @Email(message = ERR_EMAIL_INVALID)
        String email,

        @NotNull(message = ERR_ROLE_CODE)
        RoleCode roleCode,

        @NotBlank(message = ERR_PASSWORD_BLANK)
        @Length(min = 6, max = 255, message = ERR_PASSWORD_LENGTH)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = ERR_PASSWORD_PATTERN
        )
        String password
) {
    private static final String ERR_USERNAME_BLANK = "Username can not be blank";
    private static final String ERR_USERNAME_LENGTH = "Username should be between 1 - 255 characters";
    private static final String ERR_EMAIL_BLANK = "Email Address can not blank";
    private static final String ERR_ROLE_CODE = "Role must either be {ADMIN, STAFF, SUPER_ADMIN";
    private static final String ERR_EMAIL_INVALID = "Invalid Email Address";
    private static final String ERR_PASSWORD_BLANK = "Password cannot be blank";
    private static final String ERR_PASSWORD_LENGTH = "Password must be at least 6 characters long";
    private static final String ERR_PASSWORD_PATTERN = "Password must contain at least one uppercase letter, one number, and one special character.";
}
