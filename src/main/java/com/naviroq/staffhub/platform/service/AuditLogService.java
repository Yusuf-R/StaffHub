package com.naviroq.staffhub.platform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.naviroq.staffhub.common.enums.AuditAction;
import com.naviroq.staffhub.common.enums.AuditEntityType;
import com.naviroq.staffhub.platform.entity.AuditLog;
import com.naviroq.staffhub.platform.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void saveAuditLog(
            AuditAction action,
            AuditEntityType entityType,
            UUID entityId,
            String performedBy,
            JsonNode oldValue,
            JsonNode newValue,
            String reason,
            String ipAddress
    ) {

        AuditLog logEntry = AuditLog.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .performedBy(performedBy)
                .oldValue(oldValue)
                .newValue(newValue)
                .reason(reason)
                .ipAddress(ipAddress)
                .build();

        auditLogRepository.save(logEntry);

        log.info(
                "📝 Audit log saved: {} on {} (ID: {})",
                action,
                entityType,
                entityId
        );
    }
}