package com.naviroq.staffhub.identity.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = ERR_EMAIL_BLANK)
        @Email(message = ERR_EMAIL_INVALID)
        String email,

        @NotBlank(message = ERR_PASSWORD_BLANK)
        String password,

        String deviceName
) {

    private static final String ERR_EMAIL_BLANK = "Email address cannot be blank";
    private static final String ERR_EMAIL_INVALID = "Invalid email address";
    private static final String ERR_PASSWORD_BLANK = "Password cannot be blank";
}