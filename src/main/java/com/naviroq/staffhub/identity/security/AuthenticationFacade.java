package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.identity.domain.entity.User;

import java.util.UUID;

public interface AuthenticationFacade {

    User getCurrentUser();

    UUID getCurrentUserId();

    UUID getCurrentEmployeeId();

    String getCurrentUserEmail();

    boolean hasRole(String role);

}