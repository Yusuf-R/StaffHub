package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.organization.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
}