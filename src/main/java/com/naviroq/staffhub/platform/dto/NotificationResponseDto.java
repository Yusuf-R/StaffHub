package com.naviroq.staffhub.platform.dto;

import com.naviroq.staffhub.common.enums.NotificationType;
import com.naviroq.staffhub.platform.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDto(

        UUID id,

        NotificationType type,

        String title,

        String message,

        NotificationStatus status,

        UUID referenceId,

        String referenceType,

        LocalDateTime createdAt

) {
}