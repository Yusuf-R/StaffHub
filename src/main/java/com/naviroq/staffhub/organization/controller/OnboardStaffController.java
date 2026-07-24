package com.naviroq.staffhub.organization.controller;

import com.naviroq.staffhub.identity.domain.dto.UserResponseDto;
import com.naviroq.staffhub.identity.mapper.UserMapper;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffRequest;
import com.naviroq.staffhub.organization.mapper.OnboardStaffMapper;
import com.naviroq.staffhub.organization.service.OnboardStaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/staff-hub")
@RequiredArgsConstructor
public class OnboardStaffController {

    private final OnboardStaffService onboardingService;
    private final OnboardStaffMapper onboardStaffMapper;  // 👈 Inject the mapper
    private final UserMapper userMapper;

    @PostMapping("/onboarding")
    public ResponseEntity<UserResponseDto> onboardStaff(
            @Valid @RequestBody OnboardStaffRequest request
    ) {
        var command = onboardStaffMapper.createOnboardStaff(request);

        var user = onboardingService.onboardStaff(command);

        // 3. Map User → Response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toDto(user));
    }
}