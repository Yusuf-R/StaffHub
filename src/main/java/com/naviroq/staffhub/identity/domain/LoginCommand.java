package com.naviroq.staffhub.identity.domain;

public record LoginCommand(
        String email,
        String password,
        String deviceName
) {
}
