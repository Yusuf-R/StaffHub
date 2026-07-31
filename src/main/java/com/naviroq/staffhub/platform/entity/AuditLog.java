package com.naviroq.staffhub.platform.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.naviroq.staffhub.common.entity.BaseEntity;
import com.naviroq.staffhub.common.enums.AuditAction;
import com.naviroq.staffhub.common.enums.AuditEntityType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AuditEntityType entityType;

    @Column(nullable = false)
    private UUID entityId;  // The ID of the record

    @Column(nullable = false, length = 100)
    private String performedBy;  // Username of the person who did it

//    @Column(columnDefinition = "TEXT")
//    private String oldValue;  // JSON snapshot BEFORE the action

//    @Column(columnDefinition = "TEXT")
//    private String newValue;  // JSON snapshot AFTER the action (null for DELETE)

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_value", columnDefinition = "jsonb")
    private JsonNode oldValue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_value", columnDefinition = "jsonb")
    private JsonNode newValue;

    @Column(length = 500)
    private String reason;  // Why the action was performed

    @Column(length = 50)
    private String ipAddress;  // IP address of the request
}