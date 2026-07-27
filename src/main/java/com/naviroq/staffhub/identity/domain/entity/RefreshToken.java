package com.naviroq.staffhub.identity.domain.entity;

import com.naviroq.staffhub.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", schema = "staff_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

    // Store the HASH, not the plain text
    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "device_name")
    private String deviceName; // e.g., "Work Laptop", "iPhone 15"

    //Context Binding
    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    //Activity Tracking (Crucial for security)
    @Column(name = "last_used")
    private Instant lastUsed;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked = false;
}