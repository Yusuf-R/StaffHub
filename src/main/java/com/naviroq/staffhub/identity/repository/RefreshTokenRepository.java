package com.naviroq.staffhub.identity.repository;

import com.naviroq.staffhub.identity.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.userId = :userId")
    void revokeAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.userId = :userId")
    void deleteAllByUserId(@Param("userId") UUID userId);

    // Cleanup stale sessions
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.lastUsed < :cutoff AND rt.revoked = true")
    void deleteRevokedOlderThan(@Param("cutoff") Instant cutoff);
}