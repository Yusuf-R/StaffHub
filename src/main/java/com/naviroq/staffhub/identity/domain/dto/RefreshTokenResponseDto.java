package com.naviroq.staffhub.identity.domain.dto;

public record RefreshTokenResponseDto(
        String accessToken,
        Long expiresIn
) {
}
