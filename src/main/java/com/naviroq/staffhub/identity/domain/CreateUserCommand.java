package com.naviroq.staffhub.identity.domain;

import com.naviroq.staffhub.common.enums.RoleCode;

import java.util.UUID;


// used for the service layer -- no need validation
public record CreateUserCommand(
        UUID employeeId,
        String username,
        String email,
        RoleCode roleCode,
        String password
) {
}
