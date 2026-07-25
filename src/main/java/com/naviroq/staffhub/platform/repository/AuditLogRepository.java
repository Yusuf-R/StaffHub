package com.naviroq.staffhub.platform.repository;

import com.naviroq.staffhub.platform.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
}