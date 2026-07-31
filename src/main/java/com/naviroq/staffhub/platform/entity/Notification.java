package com.naviroq.staffhub.platform.entity;

import com.naviroq.staffhub.common.entity.BaseEntity;
import com.naviroq.staffhub.common.enums.NotificationType;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.platform.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications", schema = "staff_hub")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Employee recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Employee createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false,length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private NotificationStatus status = NotificationStatus.UNREAD;

    private UUID referenceId;

    @Column(length = 100)
    private String referenceType;

    private LocalDateTime readAt;
}