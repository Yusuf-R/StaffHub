package com.naviroq.staffhub.identity.domain;

import com.naviroq.staffhub.common.enums.RoleCode;


// used for the service layer -- no need validation
public record CreateUserCommand(
        String username,
        String email,
        RoleCode roleCode,
        String password
) {
}
