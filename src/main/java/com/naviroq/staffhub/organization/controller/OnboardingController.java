package com.naviroq.staffhub.organization.controller;

import com.naviroq.staffhub.identity.domain.dto.UserResponseDto;
import com.naviroq.staffhub.identity.mapper.UserMapper;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffRequest;
import com.naviroq.staffhub.organization.mapper.OnboardStaffMapper;
import com.naviroq.staffhub.organization.service.StaffOnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final StaffOnboardingService onboardingService;
    private final OnboardStaffMapper onboardStaffMapper;
    private final UserMapper userMapper;

    @PostMapping("/staff")
    public ResponseEntity<UserResponseDto> onboardStaff(@Valid @RequestBody OnboardStaffRequest request) {

        // 1. Map the Request DTO to the combined Command
        var command = onboardStaffMapper.toCommand(request);

        // 2. Execute the onboarding (creates Employee + User in one transaction)
        var user = onboardingService.onboardStaff(command);

        // 3. Map the User entity to the Response DTO and return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toDto(user));
    }
}