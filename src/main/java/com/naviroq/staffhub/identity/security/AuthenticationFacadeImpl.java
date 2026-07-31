package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new RuntimeException("No authenticated user found.");
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found."));
    }

    @Override
    public Employee getCurrentEmployee() {
        return getCurrentUser().getEmployee();
    }

    @Override
    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public UUID getCurrentEmployeeId() {
        return getCurrentEmployee().getId();
    }

    @Override
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    @Override
    public boolean hasRole(RoleCode role) {
        return getCurrentUser().getRole() == role;
    }

    @Override
    public boolean isStaff() {
        return hasRole(RoleCode.STAFF);
    }

    @Override
    public boolean isHr() {
        return hasRole(RoleCode.HR);
    }

    @Override
    public boolean isAdmin() {
        return hasRole(RoleCode.ADMIN);
    }

    @Override
    public boolean isSuperAdmin() {
        return hasRole(RoleCode.SUPER_ADMIN);
    }

    @Override
    public boolean canReviewWorkflow() {
        return isHr()
                || isAdmin()
                || isSuperAdmin();
    }

    @Override
    public void requireHr() {

        if (!isHr()) {
            throw new RuntimeException(
                    "Only HR can perform this action."
            );
        }
    }

    @Override
    public void requireAdmin() {

        if (!isAdmin() && !isSuperAdmin()) {
            throw new RuntimeException(
                    "Only Admin or Super Admin can perform this action."
            );
        }
    }

    @Override
    public void requireSuperAdmin() {

        if (!isSuperAdmin()) {
            throw new RuntimeException(
                    "Only Super Admin can perform this action."
            );
        }
    }

    @Override
    public void requireWorkflowReviewer() {

        if (!canReviewWorkflow()) {
            throw new RuntimeException(
                    "You are not authorized to review workflow requests."
            );
        }
    }
}