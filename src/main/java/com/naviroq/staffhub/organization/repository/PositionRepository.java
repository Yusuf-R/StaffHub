package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.organization.domain.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
    boolean existsByTitle(String title);

    Optional<Position> findByTitle(String title);

}