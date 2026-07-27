package com.naviroq.staffhub.identity.controller;

import com.naviroq.staffhub.identity.domain.LoginCommand;
import com.naviroq.staffhub.identity.domain.dto.LoginRequestDto;
import com.naviroq.staffhub.identity.domain.dto.LoginResponseDto;
import com.naviroq.staffhub.identity.domain.dto.RefreshTokenResponseDto;
import com.naviroq.staffhub.identity.mapper.LoginMapper;
import com.naviroq.staffhub.identity.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoginMapper loginMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid
            @RequestBody
            LoginRequestDto request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse

    ) {
        LoginCommand command = loginMapper.toCommand(request);
        return ResponseEntity.ok(authService.login(command, httpRequest, httpResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        return ResponseEntity.ok(authService.refresh(refreshToken, httpRequest, httpResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        authService.logout(refreshToken, httpRequest, httpResponse);
        return ResponseEntity.ok().build();

    }
}