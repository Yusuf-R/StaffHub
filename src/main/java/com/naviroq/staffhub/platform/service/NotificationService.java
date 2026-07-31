package com.naviroq.staffhub.platform.service;

import com.naviroq.staffhub.common.enums.NotificationType;
import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.platform.dto.NotificationResponseDto;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    void create(
            Employee recipient,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType

    );

    void notifyRole(
            RoleCode role,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    );

    void notifyWorkflowReviewers(
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    );

    void notifyDepartment(
            UUID departmentId,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    );

    void notifyWorkflowSubmission(
            Employee requester,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    );

    List<NotificationResponseDto> getMyNotifications();

    List<NotificationResponseDto> getAllNotifications();

    void markAsRead(UUID notificationId);

}