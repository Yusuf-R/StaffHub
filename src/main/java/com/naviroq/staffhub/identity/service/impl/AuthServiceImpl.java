package com.naviroq.staffhub.identity.service.impl;

import com.naviroq.staffhub.common.exception.ValidationException;
import com.naviroq.staffhub.identity.domain.LoginCommand;
import com.naviroq.staffhub.identity.domain.dto.LoginResponseDto;
import com.naviroq.staffhub.identity.domain.dto.RefreshTokenResponseDto;
import com.naviroq.staffhub.identity.domain.entity.RefreshToken;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.RefreshTokenRepository;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.identity.security.CustomUserDetails;
import com.naviroq.staffhub.identity.security.CustomUserDetailsService;
import com.naviroq.staffhub.identity.security.JwtService;
import com.naviroq.staffhub.identity.service.AuthService;
import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;
import com.naviroq.staffhub.organization.mapper.EmployeeMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeMapper employeeMapper;

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenExpiration;

    @Override
    @Transactional
    public LoginResponseDto login(LoginCommand command, HttpServletRequest request, HttpServletResponse response) {
        log.info("Login attempt for email: {}", command.email());

        // 1. Authenticate (Spring Security loads the user from DB internally)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );

        // 2. Extract the User entity from the authentication object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // getPrincipal will return our entire object - now cast to type CustomUserDetails
        assert customUserDetails != null;
        User user = customUserDetails.getUser(); // ✅ This is your User entity. No need to fetch again.

        // 3. Revoke ALL existing tokens (single session)
        refreshTokenRepository.revokeAllByUserId(customUserDetails.getUserId());

        // 4. Generate Access Token (Pass the User entity)
        String accessToken = jwtService.generateAccessToken(user);
        Long expiresIn = jwtService.getAccessTokenExpiry();

        // 5. Generate RAW Refresh Token (UUID)
        String rawRefreshToken = UUID.randomUUID().toString();
        String hashedRefreshToken = jwtService.hashToken(rawRefreshToken);

        String finalDeviceName = getDeviceName(command, request);

        // 6. Build and save RefreshToken entity
        RefreshToken refreshToken = RefreshToken.builder()
                .tokenHash(hashedRefreshToken)
                .userId(user.getId())
                .deviceName(finalDeviceName)
                .ipAddress(getClientIp(request))
                .userAgent(request.getHeader("User-Agent"))
                .lastUsed(Instant.now())
                .expiresAt(Instant.now().plusSeconds(refreshTokenExpiration))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        // 7. Set the RAW token in HttpOnly Cookie
        setRefreshTokenCookie(response, rawRefreshToken);

        // 8. Build response
        EmployeeResponseDto employee = employeeMapper.toDto(user.getEmployee());

        log.info("Login successful for email: {}", command.email());
        return new LoginResponseDto(accessToken, expiresIn, employee);
    }


    @Override
    @Transactional
    public RefreshTokenResponseDto refresh(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        log.info("Refresh token request");

        if (refreshToken == null) {
            throw new ValidationException("Refresh token not provided");
        }

        // 1. Hash the provided raw token
        String hashedToken = jwtService.hashToken(refreshToken);

        // 2. Find by hash
        RefreshToken storedToken = refreshTokenRepository.findByTokenHash(hashedToken)
                .orElseThrow(() -> new ValidationException("Invalid refresh token"));

        // 3. Validate
        if (storedToken.isRevoked()) {
            throw new ValidationException("Refresh token revoked");
        }
        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new ValidationException("Refresh token expired");
        }

        // 4. Context Binding check
        String currentIp = getClientIp(request);
        if (!storedToken.getIpAddress().equals(currentIp)) {
            log.warn("Refresh token used from different IP: {} vs {}", currentIp, storedToken.getIpAddress());
        }

        // 5. Get user
        User user = userRepository.findById(storedToken.getUserId())
                .orElseThrow(() -> new ValidationException("User not found"));

        // 6. ROTATION: Revoke the old token
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        // 7. Load the UserDetails and cast to CustomUserDetails to get the User entity
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(user.getEmail());
        User freshUser = customUserDetails.getUser(); // ✅ Extract the User entity

        // 8. Generate NEW Access Token (Pass the User entity)
        String newAccessToken = jwtService.generateAccessToken(freshUser);
        // ========== 🔥 FIX ENDS HERE ==========

        Long expiresIn = jwtService.getAccessTokenExpiry();

        // 9. Generate NEW Refresh Token (Hash and save)
        String newRawRefreshToken = UUID.randomUUID().toString();
        String newHashedRefreshToken = jwtService.hashToken(newRawRefreshToken);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .tokenHash(newHashedRefreshToken)
                .userId(user.getId())
                .deviceName(storedToken.getDeviceName())
                .ipAddress(getClientIp(request))
                .userAgent(request.getHeader("User-Agent"))
                .lastUsed(Instant.now())
                .expiresAt(Instant.now().plusSeconds(refreshTokenExpiration))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newRefreshToken);

        // 10. Set NEW cookie
        setRefreshTokenCookie(response, newRawRefreshToken);

        log.info("Refresh successful for user: {}", user.getEmail());
        return new RefreshTokenResponseDto(newAccessToken, expiresIn);
    }

    @Override
    @Transactional
    public void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        if (refreshToken != null) {
            String hashedToken = jwtService.hashToken(refreshToken);
            RefreshToken storedToken = refreshTokenRepository.findByTokenHash(hashedToken).orElse(null);

            if (storedToken != null && !storedToken.isRevoked()) {
                storedToken.setRevoked(true);
                refreshTokenRepository.save(storedToken);
                log.info("Refresh token revoked during logout");
            }
        }

        // Clear the cookie
        clearRefreshTokenCookie(response);
        log.info("Logout successful");
    }

    // ========== HELPERS ==========

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String token) {
        response.addHeader("Set-Cookie", String.format(
                "refreshToken=%s; HttpOnly; Secure; SameSite=Strict; Path=/api/v1/auth; Max-Age=%d",
                token,
                refreshTokenExpiration
        ));
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        response.addHeader("Set-Cookie",
                "refreshToken=; HttpOnly; Secure; SameSite=Strict; Path=/api/v1/auth; Max-Age=0"
        );
    }

    private String getDeviceName(LoginCommand command, HttpServletRequest request) {
        // 1. If the client sent a custom device name, use it (they might have typed "My Work Laptop")
        if (command.deviceName() != null && !command.deviceName().isBlank()) {
            return command.deviceName();
        }

        // 2. Otherwise, derive a name from the User-Agent header
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isBlank()) {
            return "Unknown Device";
        }

        // Simple parsing: Check for common browsers
        if (userAgent.contains("Chrome") && !userAgent.contains("Edg")) {
            return "Chrome Browser";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox Browser";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari Browser";
        } else if (userAgent.contains("Edg")) {
            return "Edge Browser";
        } else if (userAgent.contains("Postman")) {
            return "Postman API Client";
        } else {
            // Fallback: truncate the long User-Agent string to keep it readable
            return userAgent.length() > 50 ? userAgent.substring(0, 50) + "..." : userAgent;
        }
    }
}