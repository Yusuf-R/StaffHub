package com.naviroq.staffhub.platform.controller;

import com.naviroq.staffhub.platform.dto.NotificationResponseDto;
import com.naviroq.staffhub.platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff-hub/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping

    public List<NotificationResponseDto> myNotifications() {

        return notificationService.getMyNotifications();

    }

    @GetMapping("/all")

    public List<NotificationResponseDto> allNotifications() {

        return notificationService.getAllNotifications();

    }

    @PatchMapping("/{id}/read")

    public void markAsRead(

            @PathVariable UUID id

    ) {

        notificationService.markAsRead(id);

    }

}