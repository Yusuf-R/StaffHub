package com.naviroq.staffhub.platform.service.impl;


import com.naviroq.staffhub.common.enums.NotificationType;
import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.identity.security.AuthenticationFacade;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.platform.dto.NotificationResponseDto;
import com.naviroq.staffhub.platform.entity.Notification;
import com.naviroq.staffhub.platform.enums.NotificationStatus;
import com.naviroq.staffhub.platform.repository.NotificationRepository;
import com.naviroq.staffhub.platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void create(
            Employee recipient,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    ) {

        Notification notification =
                Notification.builder()
                        .recipient(recipient)
                        .createdBy(createdBy)
                        .type(type)
                        .title(title)
                        .message(message)
                        .referenceId(referenceId)
                        .referenceType(referenceType)
                        .build();
        notificationRepository.save(notification);
    }

    @Override
    public void notifyWorkflowReviewers(
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType

    ) {

        notifyRole(
                RoleCode.HR,
                createdBy,
                type,
                title,
                message,
                referenceId,
                referenceType
        );

        notifyRole(
                RoleCode.ADMIN,
                createdBy,
                type,
                title,
                message,
                referenceId,
                referenceType
        );

        notifyRole(
                RoleCode.SUPER_ADMIN,
                createdBy,
                type,
                title,
                message,
                referenceId,
                referenceType
        );

    }

    @Override
    public void notifyRole(
            RoleCode role,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    ) {
        userRepository
                .findAll()
                .stream()
                .filter(user -> user.getRole() == role)
                .map(User::getEmployee)
                .forEach(employee ->
                        create(
                                employee,
                                createdBy,
                                type,
                                title,
                                message,
                                referenceId,
                                referenceType
                        )
                );
    }

    @Override
    public void notifyWorkflowSubmission(
            Employee requester,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    ) {

        /*
         * Notify the requester.
         */
        create(
                requester,
                requester,
                type,
                title,
                message,
                referenceId,
                referenceType
        );

        /*
         * Notify reviewers above the requester.
         */
        switch (requester.getUser().getRole()) {

            case STAFF -> {
                notifyRole(
                        RoleCode.HR,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
                notifyRole(
                        RoleCode.ADMIN,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
                notifyRole(
                        RoleCode.SUPER_ADMIN,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
            }

            case HR -> {
                notifyRole(
                        RoleCode.ADMIN,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
                notifyRole(
                        RoleCode.SUPER_ADMIN,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
            }

            case ADMIN -> {
                notifyRole(
                        RoleCode.SUPER_ADMIN,
                        requester,
                        type,
                        title,
                        message,
                        referenceId,
                        referenceType
                );
            }

            case SUPER_ADMIN -> {
                // Nobody above Super Admin.
            }
        }
    }

    @Override
    public void notifyDepartment(
            UUID departmentId,
            Employee createdBy,
            NotificationType type,
            String title,
            String message,
            UUID referenceId,
            String referenceType
    ) {
        employeeRepository
                .findByDepartmentId(departmentId)
                .forEach(employee ->
                        create(
                                employee,
                                createdBy,
                                type,
                                title,
                                message,
                                referenceId,
                                referenceType
                        )
                );
    }

    @Override
    public List<NotificationResponseDto> getMyNotifications() {

        Employee me =
                authenticationFacade
                        .getCurrentUser()
                        .getEmployee();

        return notificationRepository

                .findByRecipientOrderByCreatedAtDesc(me)

                .stream()

                .map(notification ->

                        new NotificationResponseDto(

                                notification.getId(),

                                notification.getType(),

                                notification.getTitle(),

                                notification.getMessage(),

                                notification.getStatus(),

                                notification.getReferenceId(),

                                notification.getReferenceType(),

                                notification.getCreatedAt()

                        )

                ).toList();

    }

    @Override
    public List<NotificationResponseDto> getAllNotifications() {

        authenticationFacade.requireAdmin();

        return notificationRepository

                .findAll()

                .stream()

                .map(notification ->

                        new NotificationResponseDto(

                                notification.getId(),

                                notification.getType(),

                                notification.getTitle(),

                                notification.getMessage(),

                                notification.getStatus(),

                                notification.getReferenceId(),

                                notification.getReferenceType(),

                                notification.getCreatedAt()

                        )

                ).toList();

    }

    @Override
    public void markAsRead(UUID notificationId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow();

        notification.setStatus(NotificationStatus.READ);

        notification.setReadAt(LocalDateTime.now());

        notificationRepository.save(notification);

    }

}