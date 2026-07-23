package com.naviroq.staffhub.identity.domain.dto;

import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.common.enums.UserStatus;

import java.util.UUID;

// this will be the content of the return User object upon PUT, POST on a user credential update
public record UserDto(UUID id, String username, String email, RoleCode roleCode, UserStatus status) {
}
