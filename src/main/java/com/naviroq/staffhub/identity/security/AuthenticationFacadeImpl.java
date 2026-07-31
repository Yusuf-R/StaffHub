package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final UserRepository userRepository;

    public AuthenticationFacadeImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public UUID getCurrentEmployeeId() {
        return getCurrentUser().getEmployee().getId();
    }

    @Override
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    @Override
    public boolean hasRole(String role) {
        return getCurrentUser()
                .getRole()
                .name()
                .equalsIgnoreCase(role);
    }
}