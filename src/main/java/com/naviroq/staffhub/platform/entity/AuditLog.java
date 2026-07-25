package com.naviroq.staffhub.platform.entity;

import com.naviroq.staffhub.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "audit_logs", schema = "staff_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String action;  // "DELETE", "RESTORE", "UPDATE", "CREATE"

    @Column(nullable = false, length = 50)
    private String entityType;  // "EMPLOYEE", "USER", "DEPARTMENT"

    @Column(nullable = false)
    private UUID entityId;  // The ID of the record

    @Column(nullable = false, length = 100)
    private String performedBy;  // Username of the person who did it

    @Column(columnDefinition = "TEXT")
    private String oldValue;  // JSON snapshot BEFORE the action

    @Column(columnDefinition = "TEXT")
    private String newValue;  // JSON snapshot AFTER the action (null for DELETE)

    @Column(length = 500)
    private String reason;  // Why the action was performed

    @Column(length = 50)
    private String ipAddress;  // IP address of the request
}