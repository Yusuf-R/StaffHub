package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.common.enums.UserStatus;
import com.naviroq.staffhub.identity.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user; // 👈 Holds the actual StaffHub User entity

    // Helper method to get the User ID (used by JwtFilter)
    public UUID getUserId() {
        return user.getId();
    }

    // Helper to get the raw User entity if needed
    public User getUser() {
        return user;
    }

    // ========== SPRING SECURITY METHODS ==========

    @Override
    public String getUsername() {
        // ✅ We use EMAIL as the username for authentication
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        // Returns the hashed password stored in the database
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ✅ Translates RoleCode (e.g., "ADMIN") into Spring's "ROLE_ADMIN" format
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ✅ If UserStatus is ACTIVE, the account is enabled. Otherwise, disabled.
        return user.getStatus() == UserStatus.ACTIVE;
    }
}