package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.organization.domain.entity.Employee;

import java.util.UUID;

public interface AuthenticationFacade {

    User getCurrentUser();

    Employee getCurrentEmployee();

    UUID getCurrentUserId();

    UUID getCurrentEmployeeId();

    String getCurrentUserEmail();

    boolean hasRole(RoleCode role);

    boolean isStaff();

    boolean isHr();

    boolean isAdmin();

    boolean isSuperAdmin();

    boolean canReviewWorkflow();

    void requireHr();

    void requireAdmin();

    void requireSuperAdmin();

    void requireWorkflowReviewer();
}