package com.naviroq.staffhub.identity.service;

import com.naviroq.staffhub.identity.domain.LoginCommand;
import com.naviroq.staffhub.identity.domain.dto.LoginResponseDto;
import com.naviroq.staffhub.identity.domain.dto.RefreshTokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponseDto login(LoginCommand command, HttpServletRequest request, HttpServletResponse response);

    RefreshTokenResponseDto refresh(String refreshToken, HttpServletRequest request, HttpServletResponse response);

    void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response);
}