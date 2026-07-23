package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.organization.entity.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmploymentRepository extends JpaRepository<Employment, UUID> {
}